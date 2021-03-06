DROP TABLE VT_WF_PARAMETERIZED;
CREATE TABLE VT_WF_PARAMETERIZED (
	   input1 VARCHAR2(255),
	   input2 NUMBER(19,0),
	   result VARCHAR2(255)
 );
 
INSERT INTO  VT_WF_PARAMETERIZED VALUES ('description', 1, 'description1');
INSERT INTO  VT_WF_PARAMETERIZED VALUES ('description', 2, 'description2');
INSERT INTO  VT_WF_PARAMETERIZED VALUES ('description', 3, 'description3');

INSERT INTO  VT_WF_PARAMETERIZED VALUES ('caseID', 1, 'caseID1');
INSERT INTO  VT_WF_PARAMETERIZED VALUES ('caseID', 2, 'caseID2');
INSERT INTO  VT_WF_PARAMETERIZED VALUES ('caseID', 3, 'caseID3');

INSERT INTO  VT_WF_PARAMETERIZED VALUES ('SampleUser', 1, 'SampleUser1');
INSERT INTO  VT_WF_PARAMETERIZED VALUES ('SampleUser', 2, 'SampleUser2');
INSERT INTO  VT_WF_PARAMETERIZED VALUES ('SampleUser', 3, 'SampleUser3');

COMMIT;