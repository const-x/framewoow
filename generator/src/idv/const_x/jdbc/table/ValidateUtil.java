package idv.const_x.jdbc.table;

import java.util.ArrayList;
import java.util.List;

public class ValidateUtil {

	private static List<String> keyWords;
	
	
	public static boolean validateColumn(String column){
		column = column.trim().toUpperCase(); 
		return !keyWords.contains(column);
	}
	

	static {
		keyWords = new ArrayList<>();
		keyWords.add("ABSOLUTE");
		keyWords.add("ACTION");
		keyWords.add("ADD");
		keyWords.add("ADMINDB");
		keyWords.add("ALL");
		keyWords.add("ALLOCATE");
		keyWords.add("ALPHANUMERIC");
		keyWords.add("ALTER");
		keyWords.add("AND");
		keyWords.add("ANY");
		keyWords.add("ARE");
		keyWords.add("AS");
		keyWords.add("ASC");
		keyWords.add("ASSERTION");
		keyWords.add("AT");
		keyWords.add("AUTHORIZATION");
		keyWords.add("AUTOINCREMENT");
		keyWords.add("AVG");

		keyWords.add("BAND");
		keyWords.add("BEGIN");
		keyWords.add("BETWEEN");
		keyWords.add("BINARY");
		keyWords.add("BIT");
		keyWords.add("BIT_LENGTH");
		keyWords.add("BNOT");
		keyWords.add("BOR");
		keyWords.add("BOTH");
		keyWords.add("BXOR");
		keyWords.add("BY");
		keyWords.add("BYTE");

		keyWords.add("CASCADE");
		keyWords.add("CASCADED");
		keyWords.add("CASE");
		keyWords.add("CAST");
		keyWords.add("CATALOG");
		keyWords.add("CHAR");
		keyWords.add("CHARACTER");
		keyWords.add("CHAR_LENGTH");
		keyWords.add("CHARACTER_LENGTH");
		keyWords.add("CHECK");
		keyWords.add("CLOSE");
		keyWords.add("COALESCE");
		keyWords.add("COLLATE");
		keyWords.add("COLLATION");
		keyWords.add("COLUMN");
		keyWords.add("COMMIT");
		keyWords.add("COMP");
		keyWords.add("COMPRESSION");
		keyWords.add("CONNECT");
		keyWords.add("CONNECTION");
		keyWords.add("CONSTRAINT");
		keyWords.add("CONSTRAINTS");
		keyWords.add("CORRESPONDING");
		keyWords.add("COUNT");
		keyWords.add("CONVERT");
		keyWords.add("COUNTER");
		keyWords.add("CONTINUE");
		keyWords.add("CREATE");
		keyWords.add("CREATEDB");
		keyWords.add("CROSS");
		keyWords.add("CURRENCY");
		keyWords.add("CURRENT");
		keyWords.add("CURRENT_DATE");
		keyWords.add("CURRENT_TIME");
		keyWords.add("CURRENT_TIMESTAMP");
		keyWords.add("CURRENT_USER");
		keyWords.add("CURSOR");

		keyWords.add("DATABASE");
		keyWords.add("DATE");
		keyWords.add("DATETIME");
		keyWords.add("DAY");
		keyWords.add("DEALLOCATE");
		keyWords.add("DEC");
		keyWords.add("DECIMAL");
		keyWords.add("DECLARE");
		keyWords.add("DEFAULT");
		keyWords.add("DEFERRABLE");
		keyWords.add("DEFERRED");
		keyWords.add("DELETE");
		keyWords.add("DESC");
		keyWords.add("DESCRIBE");
		keyWords.add("DESCRIPTOR");
		keyWords.add("DIAGNOSTICS");
		keyWords.add("DISALLOW");
		keyWords.add("DISCONNECT");
		keyWords.add("DISTINCT");
		keyWords.add("DOMAIN");
		keyWords.add("DOUBLE");
		keyWords.add("DROP");

		keyWords.add("﻿");
		keyWords.add("ELSE");
		keyWords.add("END");
		keyWords.add("END-EXEC");
		keyWords.add("ESCAPE");
		keyWords.add("EXCEPT");
		keyWords.add("EXCEPTION");
		keyWords.add("EXCLUSIVECONNECT");
		keyWords.add("EXEC");
		keyWords.add("EXECUTE");
		keyWords.add("EXISTS");
		keyWords.add("EXTERNAL");
		keyWords.add("EXTRACT");

		keyWords.add("FALSE");
		keyWords.add("FETCH");
		keyWords.add("FIRST");
		keyWords.add("FLOAT");
		keyWords.add("FLOAT4");
		keyWords.add("FLOAT8");
		keyWords.add("FOR");
		keyWords.add("FOREIGN");
		keyWords.add("FOUND");
		keyWords.add("FROM");
		keyWords.add("FULL");

		keyWords.add("GENERAL");
		keyWords.add("GET");
		keyWords.add("GLOBAL");
		keyWords.add("GO");
		keyWords.add("GOTO");
		keyWords.add("GRANT");
		keyWords.add("GROUP");
		keyWords.add("GUID");

		keyWords.add("HAVING");
		keyWords.add("HOUR");

		keyWords.add("IDENTITY");
		keyWords.add("IEEEDOUBLE");
		keyWords.add("IEEESINGLE");
		keyWords.add("IGNORE");
		keyWords.add("IMAGE");
		keyWords.add("IMMEDIATE");
		keyWords.add("IN");
		keyWords.add("INDEX");
		keyWords.add("INDICATOR");
		keyWords.add("INHERITABLE");
		keyWords.add("INITIALLY");
		keyWords.add("INNER");
		keyWords.add("INPUT");
		keyWords.add("INSENSITIVE");
		keyWords.add("INSERT");
		keyWords.add("INT");
		keyWords.add("INTEGER");
		keyWords.add("INTEGER1");
		keyWords.add("INTEGER2");
		keyWords.add("INTEGER4");
		keyWords.add("INTERSECT");
		keyWords.add("INTERVAL");
		keyWords.add("INTO");
		keyWords.add("IS");
		keyWords.add("ISOLATION");

		keyWords.add("JOIN");

		keyWords.add("KEY");

		keyWords.add("LANGUAGE");
		keyWords.add("LAST");
		keyWords.add("LEADING");
		keyWords.add("LEFT");
		keyWords.add("LEVEL");
		keyWords.add("LIKE");
		keyWords.add("LOCAL");
		keyWords.add("LOGICAL");
		keyWords.add("LOGICAL1");
		keyWords.add("LONG");
		keyWords.add("LONGBINARY");
		keyWords.add("LONGCHAR");
		keyWords.add("LONGTEXT");
		keyWords.add("LOWER");

		keyWords.add("MATCH");
		keyWords.add("MAX");
		keyWords.add("MEMO");
		keyWords.add("MIN");
		keyWords.add("MINUTE");
		keyWords.add("MONTH");

		keyWords.add("NAMES");
		keyWords.add("NATIONAL");
		keyWords.add("NATURAL");
		keyWords.add("NCHAR");
		keyWords.add("NEXT");
		keyWords.add("NO");
		keyWords.add("NOT");
		keyWords.add("NOTE");
		keyWords.add("NULL");
		keyWords.add("NULLIF");
		keyWords.add("NUMBER");
		keyWords.add("NUMERIC");

		keyWords.add("OBJECT");
		keyWords.add("OCTET_LENGTH");
		keyWords.add("OF");
		keyWords.add("OLEOBJECT");
		keyWords.add("ON");
		keyWords.add("ONLY");
		keyWords.add("OPEN");
		keyWords.add("OPTION");
		keyWords.add("OR");
		keyWords.add("ORDER");
		keyWords.add("OUTER");
		keyWords.add("OUTPUT");
		keyWords.add("OVERLAPS");
		keyWords.add("OWNERACCESS");

		keyWords.add("PAD");
		keyWords.add("PARAMETERS");
		keyWords.add("PARTIAL");
		keyWords.add("PASSWORD");
		keyWords.add("PERCENT");
		keyWords.add("PIVOT");
		keyWords.add("POSITION");
		keyWords.add("PRECISION");
		keyWords.add("PREPARE");
		keyWords.add("PRESERVE");
		keyWords.add("PRIMARY");
		keyWords.add("PRIOR");
		keyWords.add("PRIVILEGES");
		keyWords.add("PROC");
		keyWords.add("PROCEDURE");
		keyWords.add("PUBLIC");

		keyWords.add("READ");
		keyWords.add("REAL");
		keyWords.add("REFERENCES");
		keyWords.add("RELATIVE");
		keyWords.add("RESTRICT");
		keyWords.add("REVOKE");
		keyWords.add("RIGHT");
		keyWords.add("ROLLBACK");
		keyWords.add("ROWS");

		keyWords.add("SCHEMA");
		keyWords.add("SCROLL");
		keyWords.add("SECOND");
		keyWords.add("SECTION");
		keyWords.add("SELECT");
		keyWords.add("SELECTSCHEMA");
		keyWords.add("SELECTSECURITY");
		keyWords.add("SESSION");
		keyWords.add("SESSION_USER");
		keyWords.add("SET");
		keyWords.add("SHORT");
		keyWords.add("SINGLE");
		keyWords.add("SIZE");
		keyWords.add("SMALLINT");
		keyWords.add("SOME");
		keyWords.add("SPACE");
		keyWords.add("SQL");
		keyWords.add("SQLCODE");
		keyWords.add("SQLERROR");
		keyWords.add("SQLSTATE");
		keyWords.add("STRING");
		keyWords.add("SUBSTRING");
		keyWords.add("SUM");
		keyWords.add("SYSTEM_USER");

		keyWords.add("TABLE");
		keyWords.add("TABLEID");
		keyWords.add("TEMPORARY");
		keyWords.add("TEXT");
		keyWords.add("THEN");
		keyWords.add("TIME");
		keyWords.add("TIMESTAMP");
		keyWords.add("TIMEZONE_HOUR");
		keyWords.add("TIMEZONE_MINUTE");
		keyWords.add("TO");
		keyWords.add("TOP");
		keyWords.add("TRAILING");
		keyWords.add("TRANSACTION");
		keyWords.add("TRANSFORM");
		keyWords.add("TRANSLATE");
		keyWords.add("TRANSLATION");
		keyWords.add("TRIM");
		keyWords.add("TRUE");

		keyWords.add("UNION");
		keyWords.add("UNIQUE");
		keyWords.add("UNIQUEIDENTIFIER");
		keyWords.add("UNKNOWN");
		keyWords.add("UPDATE");
		keyWords.add("UPDATEIDENTITY");
		keyWords.add("UPDATEOWNER");
		keyWords.add("UPDATESECURITY");
		keyWords.add("UPPER");
		keyWords.add("USAGE");
		keyWords.add("USER");
		keyWords.add("USING");

		keyWords.add("VALUE");
		keyWords.add("VALUES");
		keyWords.add("VARBINARY");
		keyWords.add("VARCHAR");
		keyWords.add("VARYING");
		keyWords.add("VIEW");

		keyWords.add("WHEN");
		keyWords.add("WHENEVER");
		keyWords.add("WHERE");
		keyWords.add("WITH");
		keyWords.add("WORK");
		keyWords.add("WRITE");

		keyWords.add("YEAR");
		keyWords.add("YESNO");

		keyWords.add("ZONE");

	}

}
