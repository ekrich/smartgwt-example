Insert into USERS (ID, ACTIVE_FLAG, EMAIL_ADDRESS, EMPLOYEE_ID, FIRST_NAME, LAST_NAME, CREATED_DATE, CREATED_BY, UPDATED_DATE, UPDATED_BY) Values (USERS_SEQ.nextval, 'Y', 'face@raytheon.com', 'system', 'Demo', 'System', sysdate, 1, sysdate, 1)
Insert into USERS (ID, ACTIVE_FLAG, EMAIL_ADDRESS, EMPLOYEE_ID, FIRST_NAME, LAST_NAME, CREATED_DATE, CREATED_BY, UPDATED_DATE, UPDATED_BY) Values (USERS_SEQ.nextval, 'Y', 'face@raytheon.com', 'anonymous', 'Local', 'User', sysdate, 1, sysdate, 1)
Insert into USERS (ID, ACTIVE_FLAG, EMAIL_ADDRESS, EMPLOYEE_ID, FIRST_NAME, LAST_NAME, CREATED_DATE, CREATED_BY, UPDATED_DATE, UPDATED_BY) Values (USERS_SEQ.nextval, 'Y', 'erichardson3@csc.com', 'nrp0212319', 'Eric', 'Richardson', sysdate, 1, sysdate, 1)
Insert into USERS (ID, ACTIVE_FLAG, EMAIL_ADDRESS, EMPLOYEE_ID, FIRST_NAME, LAST_NAME, CREATED_DATE, CREATED_BY, UPDATED_DATE, UPDATED_BY) Values (USERS_SEQ.nextval, 'Y', 'wvneisius@raytheon.com', 'HAC56520', 'Bill', 'Neisius', sysdate, 1, sysdate, 1)
Insert into USERS (ID, ACTIVE_FLAG, EMAIL_ADDRESS, EMPLOYEE_ID, FIRST_NAME, LAST_NAME, CREATED_DATE, CREATED_BY, UPDATED_DATE, UPDATED_BY) Values (USERS_SEQ.nextval, 'Y', 'Anhvu.L.Tran-NR@raytheon.com', 'nrp0219340', 'Vu', 'Tran', sysdate, 1, sysdate, 1)
Insert into ROLE (ID, NAME) Values (ROLE_SEQ.nextval, 'ADMIN')
Insert into ROLE (ID, NAME) Values (ROLE_SEQ.nextval, 'IT_ADMIN')
Insert into ROLE (ID, NAME) Values (ROLE_SEQ.nextval, 'READ_ONLY')
Insert into USER_ROLE (USER_ID, ROLE_ID) Values (1, 1)
Insert into USER_ROLE (USER_ID, ROLE_ID) Values (1, 2)
Insert into USER_ROLE (USER_ID, ROLE_ID) Values (1, 3)
Insert into USER_ROLE (USER_ID, ROLE_ID) Values (2, 1)
Insert into USER_ROLE (USER_ID, ROLE_ID) Values (2, 2)
Insert into USER_ROLE (USER_ID, ROLE_ID) Values (2, 3)
Insert into USER_ROLE (USER_ID, ROLE_ID) Values (3, 1)
Insert into USER_ROLE (USER_ID, ROLE_ID) Values (3, 2)
Insert into USER_ROLE (USER_ID, ROLE_ID) Values (3, 3)
Insert into USER_ROLE (USER_ID, ROLE_ID) Values (4, 1)
Insert into USER_ROLE (USER_ID, ROLE_ID) Values (4, 2)
Insert into USER_ROLE (USER_ID, ROLE_ID) Values (4, 3)
Insert into USER_ROLE (USER_ID, ROLE_ID) Values (5, 1)
Insert into USER_ROLE (USER_ID, ROLE_ID) Values (5, 2)
Insert into USER_ROLE (USER_ID, ROLE_ID) Values (5, 3)