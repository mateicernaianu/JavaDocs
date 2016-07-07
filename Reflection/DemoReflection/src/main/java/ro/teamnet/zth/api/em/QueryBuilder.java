package ro.teamnet.zth.api.em;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.locks.Condition;

/**
 * Created by user on 7/7/2016.
 */
public class QueryBuilder {

    private Object tableName;
    private List<ColumnInfo> queryColumns;
    private QueryType queryType;
    private List<Condition> conditions;

    public String getValueForQuery(Object value){
        if(value instanceof String){
            return value.toString();
        }
        if(value instanceof DateFormat){
            DateFormat dateFormat = new SimpleDateFormat("mm/dd/yyyy");
            return "TO_DATE('"+dateFormat.format((Date)value)+"','mm-dd-YYYY'";
        }
        return value.toString();
    }

    public Object getTableName() {
        return tableName;
    }

    public List<ColumnInfo> getQueryColumns() {
        return queryColumns;
    }

    public void setQueryColumns(List<ColumnInfo> queryColumns) {
        this.queryColumns = queryColumns;
    }

    public QueryType getQueryType() {
        return queryType;
    }

    public List<Condition> getConditions() {
        return conditions;
    }

    public void setConditions(List<Condition> conditions) {
        this.conditions = conditions;
    }

    public QueryBuilder addCondition(Condition condition){

        if(conditions == null)
        {
            conditions = new ArrayList<Condition>();
        }
        conditions.add(condition);
        return this;
    }

    public QueryBuilder setTableName(Object tableName){

        this.tableName = tableName;
        return this;
    }

    public QueryBuilder addQueryColumns(List<ColumnInfo> queryColumns){

        this.queryColumns.addAll(queryColumns);
        return this;
    }

    public QueryBuilder setQueryType(QueryType queryType){
        this.queryType = queryType;
        return this;
    }

    public String createSelectQuery(){
        StringBuilder s = new StringBuilder();
        s.append("SELECT");
        if(queryColumns.size() == 0)
            s.append("*");
        else
        {
            int size = 0;
            for(ColumnInfo inf:queryColumns) {
                size++;
                if(size < queryColumns.size())
                    s.append(inf.getColumnInfo() + ", ");
                else
                    s.append(inf.getColumnInfo()+" ");
            }
        }
        s.append("from " + tableName);
        if(conditions.size() == 0)
        {
            System.out.println(";");
        }
        else
        {
            s.append("where ");
            int size = 0;
            for(Condition condition:conditions)
            {
                size ++;
                if(size <conditions.size())
                    s.append(condition+ " and ");
                else
                    s.append(condition+ ";");
            }
        }
        return s.toString();
    }

    public String createDeleteQuery(){
        StringBuilder s = new StringBuilder();
        s.append("DELETE FROM"+ tableName);

        if(conditions.size() == 0)
        {
            System.out.println(";");
        }
        else {
            s.append("WHERE ");
            int size = 0;
            for (Condition condition : conditions) {
                size++;
                if (size < conditions.size())
                    s.append(condition + " and ");
                else
                    s.append(condition + ";");
            }
        }
        return s.toString();
    }

    public String createUpdateQuery(){
        StringBuilder s = new StringBuilder();
        if(queryColumns.size() == 0)
            return null;
        else
        {
            s.append("UPDATE "+ tableName+ "set ");
            int size = 0;
            for(ColumnInfo inf:queryColumns) {
                size++;
                if(size < queryColumns.size())
                    s.append(inf.getColumnInfo() + ", ");
                else
                    s.append(inf.getColumnInfo() + " ");
            }
            s.append("WHERE ");
            size = 0;
            for(Condition condition:conditions)
            {
                size ++;
                if(size <conditions.size())
                    s.append(condition+ " and ");
                else
                    s.append(condition+ ";");
            }
        }
        return s.toString();
    }

    public String createInsertQuery(){
        StringBuilder s = new StringBuilder();
        s.append("INSERT INTO " + tableName);
        s.append(" VALUES(");
        int size = 0;
        for(ColumnInfo inf:queryColumns) {
            size++;
            if(size < queryColumns.size())
                s.append(inf.getColumnInfo() + ", ");
            else
                s.append(inf.getColumnInfo());
        }
        s.append(");");
        return s.toString();
    }
    public String createQuery(){
        String command = null;
        if(queryType == QueryType.SELECT){
            command = createSelectQuery();
        }
        if(queryType == QueryType.INSERT){
            command = createInsertQuery();
        }
        if(queryType == QueryType.DELETE){
            command = createDeleteQuery();
        }
        if(queryType == QueryType.UPDATE){
            command = createUpdateQuery();
        }
        return command;
    }
}
