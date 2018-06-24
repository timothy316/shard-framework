package org.timothy.shard.core.sql;

import net.sf.jsqlparser.JSQLParserException;
import net.sf.jsqlparser.expression.*;
import net.sf.jsqlparser.expression.operators.relational.ExpressionList;
import net.sf.jsqlparser.expression.operators.relational.ItemsListVisitor;
import net.sf.jsqlparser.expression.operators.relational.MultiExpressionList;
import net.sf.jsqlparser.parser.CCJSqlParserUtil;
import net.sf.jsqlparser.schema.Table;
import net.sf.jsqlparser.statement.Statement;
import net.sf.jsqlparser.statement.insert.Insert;
import net.sf.jsqlparser.statement.select.SubSelect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

/**
 * SQl解析结果
 *
 * @author zhengxun
 * @date 2018-05-24
 */
public class SqlParseAndBuilder {

    private Logger logger = LoggerFactory.getLogger(SqlParseAndBuilder.class);

    /**
     * 要解析的SQL
     */
    private String sql;

    /**
     * JSqlParse解析结果
     */
    private Statement statement;

    /**
     * 解析结果
     */
    private ParseResult parseResult;
    /**
     * 解析出的表名
     */
    private List<String> tableNames;

    /**
     * 表名、列名、列值
     */
    private List<TableValue> tableValueList;

    private String sqlAndPlaceHolder;

    private String suffixPlaceHolder = "#suffix#";

    private static String SEPARATOR = "_";


    public SqlParseAndBuilder(String sql) {
        this.sql = sql;
        long startTime = System.currentTimeMillis();
        parse(this.sql);
        if (logger.isDebugEnabled()) {
            logger.debug("解析SQL耗时" + (System.currentTimeMillis() - startTime) + "毫秒, sql:" + sql);
        }
    }

    public String createSqlPlaceWithShardId(Integer shardId){
        return sqlAndPlaceHolder.replaceAll(suffixPlaceHolder, new StringBuilder(SEPARATOR).append(shardId).toString());
    }

    /**
     * 解析SQL
     *
     * @param sql
     */
    private void parse(String sql) {
        try {
            statement = CCJSqlParserUtil.parse(sql);
        } catch (JSQLParserException e) {
            String msg = "SQL解析错误，请检查SQL是否正确，SQL:" + sql;
            logger.error(msg);
            throw new RuntimeException(msg, e);
        }
        if (statement instanceof Insert) {
            parseResult = parseInsert((Insert) statement);
        }

        List<String> tableNameList = new ArrayList<>();
        Map<Table, Table> tableMap = new HashMap<>();
        for (Table table : parseResult.getTables()) {
            tableNameList.add(table.getName());
            tableMap.put(table, table);
        }
        tableNames = Collections.unmodifiableList(tableNameList);
        tableValueList = Collections.unmodifiableList(parseResult.getTableValues());

        StringBuilder tableName = new StringBuilder();
        for (Table table : tableMap.keySet()) {
            if(tableName.length() > 0){
                tableName.delete(0, tableName.length());
            }
            tableName.append(table.getName())
                    .append(suffixPlaceHolder);
            table.setName(tableName.toString());
        }
        sqlAndPlaceHolder = statement.toString();
    }

    /**
     * 解析insert语句
     *
     * @param insert
     * @return
     */
    private ParseResult parseInsert(Insert insert) {
        final InsertParseResult insertParseResult = new InsertParseResult();
        insertParseResult.table = insert.getTable();
        insertParseResult.tableValue.setTableName(insert.getTable().getName());
        if (insert.getItemsList() != null) insert.getItemsList().accept(new ItemsListVisitor() {
            @Override
            public void visit(SubSelect subSelect) {
                throw new RuntimeException("不支持的语法结构");
            }

            @Override
            public void visit(ExpressionList expressionList) {
                if (expressionList.getExpressions().size() > 0) {
                    String value = null;
                    for (int i = 0; i < expressionList.getExpressions().size(); i++) {
                        Expression expression = expressionList.getExpressions().get(i);
                        if (expression instanceof LongValue) {
                            value = String.valueOf(((LongValue) expression).getValue());
                        } else if (expression instanceof DoubleValue) {
                            value = String.valueOf(((DoubleValue) expression).getValue());
                        } else if (expression instanceof DateValue) {
                            value = String.valueOf(((DateValue) expression).getValue());
                        } else if (expression instanceof TimeValue) {
                            value = String.valueOf(((TimeValue) expression).getValue());
                        } else if (expression instanceof TimestampValue) {
                            value = String.valueOf(((TimestampValue) expression).getValue());
                        } else if (expression instanceof StringValue) {
                            value = ((StringValue) expression).getValue();
                        } else if (expression instanceof JdbcParameter) {
                            value = expression.toString();
                        } else {
                            throw new RuntimeException("不支持的数据格式");
                        }
                        if (value != null) {
                            ColumnValue columnValue = new ColumnValue();
                            columnValue.setColumnName(insert.getColumns().get(i).getColumnName());
                            columnValue.setColumnValue(value);
                            insertParseResult.tableValue.getColumnValueList().add(columnValue);
                        }
                    }
                }
            }

            @Override
            public void visit(MultiExpressionList multiExprList) {
                for (ExpressionList expressionList : multiExprList.getExprList()) {
                    this.visit(expressionList);
                }
            }
        });
        return insertParseResult;
    }


    private interface ParseResult {
        List<TableValue> getTableValues();

        List<Table> getTables();
    }

    private class InsertParseResult implements ParseResult {

        private Table table;

        private TableValue tableValue = new TableValue();


        @Override
        public List<TableValue> getTableValues() {
            List<TableValue> tableValueList = new ArrayList<>();
            tableValueList.add(tableValue);
            return tableValueList;
        }

        @Override
        public List<Table> getTables() {
            List<Table> tableList = new ArrayList<>();
            tableList.add(table);
            return tableList;
        }

        @Override
        public String toString() {
            return "InsertParseResult{" +
                    "table=" + table +
                    ", tableValue=" + tableValue +
                    '}';
        }
    }

    public String getSqlAndPlaceHolder() {
        return sqlAndPlaceHolder;
    }

    public String getSql() {
        return sql;
    }

    public Statement getStatement() {
        return statement;
    }

    public ParseResult getParseResult() {
        return parseResult;
    }

    public List<String> getTableNames() {
        return tableNames;
    }

    public List<TableValue> getTableValueList() {
        return tableValueList;
    }

    @Override
    public String toString() {
        return "SqlParseAndBuilder{" +
                "sql='" + sql + '\'' +
                ", statement=" + statement +
                ", parseResult=" + parseResult +
                ", tableNames=" + tableNames +
                ", tableValueList=" + tableValueList +
                '}';
    }
}
