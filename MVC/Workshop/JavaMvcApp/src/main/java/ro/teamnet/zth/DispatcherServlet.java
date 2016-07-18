package ro.teamnet.zth;

import org.codehaus.jackson.map.ObjectMapper;
import ro.teamnet.zth.api.annotations.MyBodyParam;
import ro.teamnet.zth.api.annotations.MyController;
import ro.teamnet.zth.api.annotations.MyRequestMethod;
import ro.teamnet.zth.api.annotations.MyRequestParam;
import ro.teamnet.zth.appl.controller.DepartmentController;
import ro.teamnet.zth.appl.controller.EmployeeController;
import ro.teamnet.zth.fmk.AnnotationScanUtils;
import ro.teamnet.zth.fmk.MethodAttributes;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by user on 7/15/2016.
 */
public class DispatcherServlet extends HttpServlet {

    HashMap<String, MethodAttributes> allowedMethods = new HashMap<String, MethodAttributes>();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        dispatchReply("GET", req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        dispatchReply("POST", req, resp);
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        dispatchReply("DELETE", req, resp);
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        dispatchReply("PUT", req, resp);
    }

    @Override
    public void init() throws ServletException {
        try {
            Iterable<Class> controllers = AnnotationScanUtils.getClasses("ro.teamnet.zth.appl.controller");
            for(Class controller : controllers){
                if(controller.isAnnotationPresent(MyController.class)){
                    MyController myCtrlAnnotation = (MyController)controller.getAnnotation(MyController.class);
                    String controllerUrlPath = myCtrlAnnotation.urlPath();
                    for(Method controllerMethod : controller.getMethods()){
                        if(controllerMethod.isAnnotationPresent(MyRequestMethod.class)){
                            MyRequestMethod myRequestMethod = (MyRequestMethod) controllerMethod.getAnnotation(MyRequestMethod.class);
                            String methodUrlPath = myRequestMethod.urlPath();
                            String urlPath = controllerUrlPath + methodUrlPath;

                            MethodAttributes methodAttributes = new MethodAttributes();
                            methodAttributes.setControllerClass(controller.getName());
                            methodAttributes.setMethodType(myRequestMethod.toString());
                            methodAttributes.setMethodName(controllerMethod.getName());
                            methodAttributes.setParameterTypes(controllerMethod.getParameterTypes());

                            allowedMethods.put(urlPath, methodAttributes);
                        }
                    }
                }
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    protected void dispatchReply(String identifier, HttpServletRequest req, HttpServletResponse resp)  {
        Object r = null;
        try {
            r = dispatch(req, resp);
        }catch(Exception e){
            sendExceptionError(e, req, resp);
        }
        try {
            reply(r, req, resp);
        } catch (Exception e) {
            sendExceptionError(e, req, resp);}
    }

    protected Object dispatch(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String path = req.getPathInfo();
        MethodAttributes methodAttributes = allowedMethods.get(path);
        if (methodAttributes == null) {
            return "Hello";
        }else {
            String controllerName = methodAttributes.getControllerClass();
            try {
                Class<?> controllerClass = Class.forName(controllerName);
                Object controllerInstance = controllerClass.newInstance();
                Method method = controllerClass.getMethod(methodAttributes.getMethodName(), methodAttributes.getParameterTypes());

                List<Object> parameterValues = new ArrayList<>();
                for(Parameter p : method.getParameters()){
                    if(p.isAnnotationPresent(MyRequestParam.class)){
                        MyRequestParam annotation = p.getAnnotation(MyRequestParam.class);
                        String name = annotation.name();
                        String requestParamValue = req.getParameter(name);
                        Class<?> type = p.getType();
                        Object requestParamObject = null;
                        if(type.equals(String.class)){
                            requestParamObject = requestParamValue;
                        }
                        else {
                            requestParamObject = new ObjectMapper().readValue(requestParamValue, type);
                        }
                        parameterValues.add(requestParamObject);
                    }
                    else{
                        if(p.isAnnotationPresent(MyBodyParam.class)){
                            BufferedReader requestBodyReader = req.getReader();
                            Object requestBodyObject =  new ObjectMapper().readValue(requestBodyReader, p.getType());
                            parameterValues.add(requestBodyObject);
                        }
                    }
                }

                Object r = method.invoke(controllerInstance, parameterValues.toArray());
                return r;
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
        return null;
    }



    protected void reply(Object r, HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        PrintWriter pr = resp.getWriter();
        pr.printf(objectMapper.writeValueAsString(r));
    }

    protected void sendExceptionError(Exception e, HttpServletRequest req, HttpServletResponse resp)  {
    }
}