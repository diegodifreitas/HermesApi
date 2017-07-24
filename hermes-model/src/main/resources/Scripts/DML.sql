/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 * Author:  Diego Dulval
 * Created: 11/05/2017
 */

-- FILE --------------------------------------------------------------------------------------------------
SELECT * FROM file;
INSERT INTO file (fil_name, fil_url, fil_divulgation_fk) 
    VALUES ('FIle_Notice', 'file.png', 1) 
 RETURNING fil_id;
DELETE FROM file WHERE fil_id=2;
----------------------------------------------------------------------------------------------------------
-- DIVULGATION -------------------------------------------------------------------------------------------
SELECT * FROM divulgation;
INSERT INTO divulgation (div_type, div_title, div_description, div_created_at) 
    VALUES ('event', 'Teste PGAdmin Event', 'Teste', NOW()) 
RETURNING div_id;
DELETE FROM divulgation WHERE div_id=2;
----------------------------------------------------------------------------------------------------------




