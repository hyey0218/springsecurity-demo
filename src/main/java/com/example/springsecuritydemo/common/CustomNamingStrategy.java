package com.example.springsecuritydemo.common;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.boot.model.naming.Identifier;
import org.hibernate.engine.jdbc.env.spi.JdbcEnvironment;
import org.springframework.boot.orm.jpa.hibernate.SpringPhysicalNamingStrategy;

public class CustomNamingStrategy extends SpringPhysicalNamingStrategy {

    private static final String TABLE_PREFIX = "TB_";
    private static final String AUDIT_TABLE_SUFFIX = "_aud";

    @Override
    public Identifier toPhysicalTableName(Identifier name, JdbcEnvironment context) {
        Identifier tableName = super.toPhysicalTableName(name, context);
        if (StringUtils.contains(tableName.getText(), AUDIT_TABLE_SUFFIX)) {
            return new Identifier(StringUtils.replace(tableName.getText(), StringUtils.lowerCase(TABLE_PREFIX), TABLE_PREFIX), tableName.isQuoted());
        } else {
            return new Identifier(TABLE_PREFIX + tableName.getText(), tableName.isQuoted());
        }
    }

}
