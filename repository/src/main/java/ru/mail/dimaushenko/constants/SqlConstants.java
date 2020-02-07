package ru.mail.dimaushenko.constants;

public interface SqlConstants {

    String SQL_REQUEST_DATABASE_CREATE = "sql.request.database.homework11_12.create";
    String SQL_REQUEST_DATABASE_DROP = "sql.request.database.homework11_12.drop";
    String SQL_REQUEST_DATABASE_SHOW = "sql.request.database.homework11_12.show";
    String SQL_REQUEST_DATABASE_GET_TABLES = "sql.request.database.homework11_12.gettables";

    String SQL_REQUEST_TABLE_USER_CREATE = "sql.request.table.user.create";
    String SQL_REQUEST_TABLE_USER_DROP = "sql.request.table.user.drop";
    String SQL_REQUEST_TABLE_USER_INSERT = "sql.request.table.user.insert";
    String SQL_REQUEST_TABLE_USER_SELECT_ALL = "sql.request.table.user.select.all";
    String SQL_REQUEST_TABLE_USER_SELECT_PASSWORD_BY_USERNAME = "sql.request.table.user.select.password.by.username";

    String SQL_REQUEST_TABLE_ROLE_CREATE = "sql.request.table.role.create";
    String SQL_REQUEST_TABLE_ROLE_DROP = "sql.request.table.role.drop";
    String SQL_REQUEST_TABLE_ROLE_INSERT = "sql.request.table.role.insert";
    String SQL_REQUEST_TABLE_ROLE_SELECT_ALL = "sql.request.table.role.select_all";

    String SQL_DATABASE_NAME = "sql.database.name";

    String SQL_TABLE_NAME_USER = "sql.table_name.user";
    String SQL_TABLE_NAME_ROLE = "sql.table_name.role";

    String SQL_COLUMN_DATABASE = "sql.column.database";
    String SQL_COLUMN_DATABASE_TABLES = "sql.column.database.tables";

    String SQL_COLUMN_TABLE_USER_ID = "sql.column.table.user.id";
    String SQL_COLUMN_TABLE_USER_USERNAME = "sql.column.table.user.username";
    String SQL_COLUMN_TABLE_USER_PASSWORD = "sql.column.table.user.password";
    String SQL_COLUMN_TABLE_USER_CREATED_BY = "sql.column.table.user.created_by";
    String SQL_COLUMN_TABLE_USER_ROLE_ID = "sql.column.table.user.role_id";

    String SQL_COLUMN_TABLE_ROLE_ID = "sql.column.table.role.id";
    String SQL_COLUMN_TABLE_ROLE_NAME = "sql.column.table.role.name";
    String SQL_COLUMN_TABLE_ROLE_DESCRIPTION = "sql.column.table.role.description";

    String FORM_INPUT_NAME_USERNAME = "form.input.name.username";
    String FORM_INPUT_NAME_PASSWORD = "form.input.name.password";

}
