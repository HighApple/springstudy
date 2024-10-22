DROP TABLE ADDRESS_T;
DROP TABLE MEMBER_T;

CREATE TABLE MEMBER_T (
    MEMBER_NO       NUMBER              NOT NULL,
    EMAIL           VARCHAR2(100 BYTE),
    NAME            VARCHAR2(100 BYTE),
    GENDER          VARCHAR2(5 BYTE),
    CONSTRAINT PK_MEMBER PRIMARY KEY(MEMBER_NO)
);

CREATE TABLE ADDRESS_T (
    ADDRESS_NO      NUMBER              NOT NULL,
    ZONECODE        CHAR(5 BYTE),
    ADDRESS         VARCHAR2(100 BYTE),
    DETAIL_ADDRESS  VARCHAR2(100 BYTE),
    EXTRA_ADDRESS   VARCHAR2(100 BYTE),
    MEMBER_NO       NUMBER,
    CONSTRAINT PK_ADDRESS PRIMARY KEY(ADDRESS_NO),
    CONSTRAINT FK_ADDRESS_MEMBER FOREIGN KEY(MEMBER_NO)
        REFERENCES MEMBER_T(MEMBER_NO)
            ON DELETE CASCADE
);

DROP SEQUENCE MEMBER_SEQ;
DROP SEQUENCE ADDRESS_SEQ;

CREATE SEQUENCE MEMBER_SEQ START WITH 1001 NOCACHE;
CREATE SEQUENCE ADDRESS_SEQ START WITH 1001 NOCACHE;

SELECT RN, MEMBER_NO, EMAIL, NAME, GENDER, ADDRESS_NO, ZONECODE, DETAIL_ADDRESS, EXTRA_ADDRESS
  FROM (SELECT ROW_NUMBER() OVER(ORDER BY M.MEMBER_NO DESC) AS RN
             , M.MEMBER_NO, M.EMAIL, M.NAME, M.GENDER
             , A.ADDRESS_NO, A.ZONECODE, A.ADDRESS, A.DETAIL_ADDRESS, A.EXTRA_ADDRESS
          FROM MEMBER_T M INNER JOIN ADDRESS_T A
            ON M.MEMBER_NO = A.MEMBER_NO)
 WHERE RN BETWEEN 1 AND 20;