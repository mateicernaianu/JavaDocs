package ro.teamnet.zth;

import org.codehaus.jackson.map.ObjectMapper;
import ro.teamnet.zth.api.annotations.MyController;
import ro.teamnet.zth.api.annotations.MyRequestMethod;
import ro.teamnet.zth.api.annotations.MyRequestParam;
import ro.teamnet.zth.appl.controller.DepartmentController;
import ro.teamnet.zth.appl.controller.EmployeeController;
import ro.teamnet.zth.appl.domain.Department;
import ro.teamnet.zth.fmk.AnnotationScanUtils;
import ro.teamnet.zth.fmk.MethodAttributes;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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
 * Created by user on 7/14/2016.
 */
public class DispatcherServlet extends HttpServlet{

    //rol de registru
    //key: urlPath
    //val: informatii despre metoda care va procesa acest url
    HashMap<String,MethodAttributes> allowedMethods = new HashMap<String, MethodAttributes>();
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        dipatchReply(req, resp,"GET");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        dipatchReply(req, resp,"POST");
    }

    public void dipatchReply(HttpServletRequest req, HttpServletResponse resp, String identifier)  {

        Object r = null;
        try {
             r = dispatch(req, resp);

        }
        catch(Exception e){
            sendExceptionError(e,req, resp);
        }
        try {
            reply(r,req,resp);
        } catch (IOException e) {
            sendExceptionError(e,req, resp);
        }
    }

    private void sendExceptionError(Exception e, HttpServletRequest req, HttpServletResponse resp) {

    }

    private void reply(Object r, HttpServletRequest req, HttpServletResponse resp) throws IOException {

        ObjectMapper objectMapper = new ObjectMapper();
        PrintWriter out = resp.getWriter();
        out.printf(objectMapper.writeValueAsString(r));
    }

    @Override
    public void init() throws ServletException {
        try {
            Iterable<Class> controllers = AnnotationScanUtils.getClasses("ro.teamnet.zth.appl.controller");
            for(Class controller:controllers){
                if(controller.isAnnotationPresent(MyController.class))
                {
                    MyController myCtrlAnnotation = (MyController) controller.getAnnotation(MyController.class);
                    String controllerUrlPath = myCtrlAnnotation.urlPath();
                    Method[] controllerMethods = controller.getMethods();
                    for(Method controllerMethod : controllerMethods) {
                        if (controllerMethod.isAnnotationPresent(MyRequestMethod.class)) {
                            MyRequestMethod myReqMethod = (MyRequestMethod) controllerMethod.getAnnotation(MyRequestMethod.class);
                            String methodUrl = myReqMethod.urlPath();
                            String urlPath = controllerUrlPath + methodUrl;

                            MethodAttributes methodAttributes = new MethodAttributes();
                            methodAttributes.setControllerClass(controller.getName());
                            methodAttributes.setMethodType(myReqMethod.toString());
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

    private Object dispatch(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        String path = req.getPathInfo();

        /*
        if(path.startsWith("/employee"))
        {
            EmployeeController employeeController = new EmployeeController();
            String result = employeeController.getAllEmployees();
            return result;
        }
        if(path.startsWith("/departments"))
        {
            DepartmentController departmentController = new DepartmentController();
            String result = departmentController.getAllDepartments();
            return result;
        }
        */
        if (allowedMethods.get(path) == null)
            return "hello";


        String controllerName = allowedMethods.get(path).getControllerClass();
        Object result;
        result = null;
        try {
            Class<?> controllerClass = Class.forName(controllerName);

            Object controllerInstance = controllerClass.newInstance();
            Method method = controllerClass.getMethod(allowedMethods.get(path).getMethodName(), allowedMethods.get(path).getParameterTypes() );
            Parameter[] parameters = method.getParameters();
            List<Object> parameterValues = new ArrayList<Object>();
            for(Parameter parameter: parameters){
                MyRequestParam annotation = parameter.getAnnotation(MyRequestParam.class);
                String name = annotation.name();
                String requestParamValue = req.getParameter(name);
                Class<?> type = parameter.getType();
                Object requestParamObject = new ObjectMapper().readValue(requestParamValue,type);
                parameterValues.add(requestParamObject);
            }

            result = method.invoke(controllerInstance, parameterValues.toArray());
            return result;
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        return result;
    }
}
