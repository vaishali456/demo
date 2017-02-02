package com.medq.util;

public class Storeproc {

    /* Signup Store Proc */
 /* 
   
     -- --------------------------------------------------------------------------------
     -- Routine DDL     16 AUG 4:12
     -- Note: comments before and after the routine body will not be stored by the server
     -- --------------------------------------------------------------------------------
     DELIMITER $$

     CREATE DEFINER=`root`@`localhost` PROCEDURE `SignupProc`(
     in houseno tinytext,
     in street tinytext,
     in cityid int,
     in stateid int,
     in zipcode tinytext,
     in clinicName tinytext,
     in faxNo tinytext,
     in phoneNo tinytext,
     in StaffCount int,
     in DoctorCount int,
     in clinicEmail tinytext,
     in staffDtl longtext,
     in doctorDtl longtext)
     BEGIN


     DECLARE clinicAddressID INT;
     DECLARE clinicID INT;
     DECLARE docID INT;
     DECLARE docAddrID INT ;

     DECLARE selectedUser longtext;
     DECLARE docAddress longtext;

     DECLARE credential longtext;
     DECLARE doctors longtext;
     DECLARE insurtype longtext;
     declare specReport tinytext;
     DECLARE buisnessDay longtext;
     DECLARE currDay longtext;

     DECLARE intItr int;




     DECLARE fn longtext ;
     DECLARE ln longtext ;
     DECLARE dp longtext ;
     DECLARE cp longtext ;
     DECLARE email longtext ;
     DECLARE rid INT ;
     DECLARE pwd longtext ;
     DECLARE notify bit ;

     DECLARE add1 longtext ;
     DECLARE add2 longtext ;
     DECLARE cid INT ;
     DECLARE sid INT ;
     DECLARE zipC longtext ;



     DECLARE docfn longtext ;
     DECLARE docln longtext ;
     DECLARE docdp longtext ;
     DECLARE doccp longtext ;
     DECLARE docfax longtext ;
     DECLARE specID int ;
     DECLARE docpwd longtext ;
     DECLARE docemail longtext ;
     DECLARE isdoconlycontact bit ;
     DECLARE docrid INT ;

     DECLARE Sun bit default false ;
     DECLARE Mon bit default false;
     DECLARE Tue bit default false ;
     DECLARE Wed bit default false ;
     DECLARE Thur bit default false ;
     DECLARE Fri bit default false;
     DECLARE Sat bit default false ;




     INSERT INTO `medqapp`.`Address`(`HouseNo`,`Street`,`CityID`,`StateID`,`ZipCode`)
     VALUES (houseno,street,cityid,stateid,zipcode);

     set clinicAddressID = LAST_INSERT_ID();

     INSERT INTO `medqapp`.`Clinic`
     (`ClinicName`,`FaxNo`,`AdressID`,`PhoneNo`,`StaffCount`,`DoctorCount`,`Email`)
     VALUES
     (clinicName,faxNo,clinicAddressID,phoneNo,StaffCount,DoctorCount,clinicEmail);

     set clinicID = LAST_INSERT_ID();

     -- Loop to Insert all staff 
     WHILE StaffCount > 0 DO
 
     set selectedUser = SPLIT_STR(staffDtl, '|', StaffCount) ;


     set fn= SPLIT_STR(selectedUser, ',', 1) ;
     set ln= SPLIT_STR(selectedUser, ',', 2)   ;
     set dp= SPLIT_STR(selectedUser, ',', 3)  ;
     set cp= SPLIT_STR(selectedUser, ',', 4)  ;
     set pwd= SPLIT_STR(selectedUser, ',', 5)  ;
     set email= SPLIT_STR(selectedUser, ',', 6)  ;
     set notify= SPLIT_STR(selectedUser, ',', 7)  ; 
     set rid= SPLIT_STR(selectedUser, ',', 8)  ; 

 
     INSERT INTO `medqapp`.`User`
     (`FirstName`,`LastName`,`ClinicID`,`AddressID`,`DeskPhone`,
     `CellPhone`,`Password`,`Email`,`IsNotificationON`,`RoleID`)
     VALUES(fn,ln,clinicID,clinicAddressID,dp,cp,pwd,email,notify,rid);

 
     SET  StaffCount = StaffCount - 1; 
     END WHILE;


     -- Loop to Insert all Doc 
     WHILE DoctorCount > 0 DO
 
     set selectedUser = SPLIT_STR(doctorDtl, '|||', DoctorCount) ;
     set docAddress   = SPLIT_STR(selectedUser, '||', 1) ;
     set credential   = SPLIT_STR(selectedUser, '||', 2) ;
     set insurtype    = SPLIT_STR(selectedUser, '||', 3) ;
     set buisnessDay    = SPLIT_STR(selectedUser, '||', 4) ;
     set specReport    = SPLIT_STR(selectedUser, '||', 5) ;
     set doctors      = SPLIT_STR(selectedUser, '||', 6) ;
   



     set add1= SPLIT_STR(docAddress, ',', 1) ;
     set add2= SPLIT_STR(docAddress, ',', 2)   ;
     set cid= SPLIT_STR(docAddress, ',', 3)  ;  
     set sid= SPLIT_STR(docAddress, ',', 4)  ;
     set zipC= SPLIT_STR(docAddress, ',', 5)  ;

     INSERT INTO `medqapp`.`Address`(`HouseNo`,`Street`,`CityID`,`StateID`,`ZipCode`)
     VALUES (add1,add2,cid,sid,zipC);
 
     set docAddrID= LAST_INSERT_ID();



     set docfn= SPLIT_STR(doctors, ',', 1) ;
     set docln= SPLIT_STR(doctors, ',', 2) ;
     set docdp= SPLIT_STR(doctors, ',', 3) ;
     set doccp= SPLIT_STR(doctors, ',', 4) ;
     set docfax= SPLIT_STR(doctors, ',', 5) ;
     set specID= SPLIT_STR(doctors, ',', 6) ;
     set docpwd= SPLIT_STR(doctors, ',', 7) ;
     set docemail= SPLIT_STR(doctors, ',', 8) ;
     set isdoconlycontact= SPLIT_STR(doctors, ',', 9) ; 
     set docrid= SPLIT_STR(doctors, ',', 10)  ;  


     INSERT INTO `medqapp`.`User`
     (`FirstName`,`LastName`,`ClinicID`,`AddressID`,`DeskPhone`,`CellPhone`,
     `FaxNo`,`SpecializationID`,`Password`,`Email`,`IsDocOnlyContact`,`RoleID`)
     VALUES(docfn,docln,clinicID,docAddrID,docdp,doccp,docfax,specID,docpwd,docemail,isdoconlycontact,docrid);

     set docID=LAST_INSERT_ID();

     -- Insert Credentialas 

     set intItr=(LENGTH(credential) - LENGTH(REPLACE(credential,",","")) + 1); 
     WHILE intItr > 0 DO

     INSERT INTO `medqapp`.`DoctorCredential`(`DoctorID`,`CredentialID`) VALUES(docID,SPLIT_STR(credential, ',', intItr)); 

     SET  intItr = intItr - 1; 
     END WHILE; 

     -- Insert Insurance 

     set intItr=(LENGTH(insurtype) - LENGTH(REPLACE(insurtype,",","")) + 1); 
     WHILE intItr > 0 DO

     INSERT INTO `medqapp`.`InsuranceDoctor`
     (`DoctorID`,`InsuranceID`)VALUES(docID,SPLIT_STR(insurtype, ',', intItr));

     SET  intItr = intItr - 1; 
     END WHILE; 


  
     -- Insert working day 	
     set intItr=(LENGTH(buisnessDay) - LENGTH(REPLACE(buisnessDay,",","")) + 1); 
     WHILE intItr > 0 DO
     set currDay=SPLIT_STR(buisnessDay, ',', intItr);
		
     case currDay 
     when 'Sunday'    then   set Sun = true; 
     when 'Monday'    then   set Mon = true;  
     when 'Tuesday'   then   set Tue = true;  
     when 'Wednesday' then   set Wed = true;  
     when 'Thursday'  then   set Thur = true;  
     when 'Friday'    then   set Fri = true;  
     when 'Satday'    then   set Sat = true;  
	

     end case;  	

 
     SET  intItr = intItr - 1; 
     END WHILE; 

     INSERT INTO `medqapp`.`DoctorSchedule`
     (`DoctorID`,`Monday`,`Tuesday`,`Wednesday`,`Thursday`,`Friday`,`Saturday`,`Sunday`)
     VALUES (docID,Mon,Tue,Wed,Thur,Fri,Sat,Sun); 

     -- specialistReport

     set intItr=(LENGTH(specReport) - LENGTH(REPLACE(specReport,",","")) + 1); 
     WHILE intItr > 0 DO

     INSERT INTO `medqapp`.`SpecialistReport`
     (`DoctorID`,`RequiredReportID`)
     VALUES
     (docID,SPLIT_STR(specReport, ',', intItr));


     SET  intItr = intItr - 1; 
     END WHILE; 


     SET  DoctorCount = DoctorCount - 1; 
     END WHILE; 


     END
    
    
     */
 /*---------- Create Referral Store Proc -----------------------------*/
 /*
    
     -- --------------------------------------------------------------------------------
     -- Routine DDL   17 aug 4:13 P.M.
     -- Note: comments before and after the routine body will not be stored by the server
     -- --------------------------------------------------------------------------------
     DELIMITER $$

     CREATE DEFINER=`root`@`localhost` PROCEDURE `CreateReferralProc`(in ReferralDtl longtext)
     BEGIN
     declare referral longtext;
     declare referralDisease longtext;
     declare referrTo longtext;
     declare docList longtext;


     declare  referrBy int;
     declare  urgency tinytext;
     declare  patientID int;
     declare  createdTime timestamp;
    
     declare intItr int;
     declare referralID int;


    
     set referral = SPLIT_STR(ReferralDtl, '|#refque!|', 1) ;  
     set referralDisease = SPLIT_STR(ReferralDtl, '|#refque!|', 2) ;
     set referrTo = SPLIT_STR(ReferralDtl, '|#refque!|', 3) ;
     set docList =  SPLIT_STR(ReferralDtl, '|#refque!|', 4) ;

     -- CreateReferral
     set referrBy= SPLIT_STR(referral, ',', 1) ;
     set urgency= SPLIT_STR(referral, ',', 2)   ;
     set patientID= SPLIT_STR(referral, ',', 3)  ;
     set createdTime= SPLIT_STR(referral, ',', 4)  ;

     INSERT INTO `medqapp`.`Referral`
     (`ReferredBy`,`Urgency`,`PatientID`,`CreatedTime`)
     VALUES (referrBy,urgency,patientID,createdTime);

     set referralID = LAST_INSERT_ID();

     -- Referral Diseaes     
     set intItr=(LENGTH(referralDisease) - LENGTH(REPLACE(referralDisease,",","")) + 1); 
     WHILE intItr > 0 DO
    
     INSERT INTO `medqapp`.`ReferralDisease` (`ReferralID`,`DiseaseID`) VALUES (referralID,SPLIT_STR(referralDisease, ',', intItr));
	
     SET  intItr = intItr - 1; 
     END WHILE; 

     -- Referred To
     set intItr=(LENGTH(referrTo) - LENGTH(REPLACE(referrTo,",","")) + 1); 
     WHILE intItr > 0 DO
    
     INSERT INTO `medqapp`.`ReferredTo`
     (`ReferralID`,`SpecialistID`,`ReferredBy`,`ReferredToDate`)
     VALUES
     (referralID,SPLIT_STR(referrTo, ',', intItr),referrBy,createdTime);
	
     SET  intItr = intItr - 1; 
     END WHILE; 

     -- Insert Document

     set intItr=(LENGTH(docList) - LENGTH(REPLACE(docList,",","")) + 1); 
     WHILE intItr > 0 DO
	

     INSERT INTO `medqapp`.`Document`
     (`DocumentUrl`,`UploadedBy`,`UploadTime`,`ReferralID`)
     VALUES
     (SPLIT_STR(singleDoc, ',', intItr),referrBy,createdTime,referralID);
		

     SET  intItr = intItr - 1; 
     END WHILE; 


     END
     
    
     */
 /*
     -- -------------- Function to spilt string used in signup proc ---------------
    
  
     -- --------------------------------------------------------------------------------
     -- Routine DDL
     -- Note: comments before and after the routine body will not be stored by the server
     -- --------------------------------------------------------------------------------
     DELIMITER $$

     CREATE DEFINER=`root`@`localhost` FUNCTION `SPLIT_STR`(
     x longtext,
     delim VARCHAR(12),
     pos INT
     ) RETURNS longtext CHARSET latin1
     RETURN REPLACE(SUBSTRING(SUBSTRING_INDEX(x, delim, pos),
     CHAR_LENGTH(SUBSTRING_INDEX(x, delim, pos -1)) + 1),
     delim, '') 
    
    
     */
}
