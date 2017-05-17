<b>To build:</b>  
gradle build

<b>To start server:</b>  
java -jar build/libs/wandoo-1.0.0.jar (or just run Application.java from your IDE) 

<b>To execute on Linux:</b>  
GET: curl -i -H "Content-Type: application/json" -X GET $URL  
POST: curl -i -H "Content-Type: application/json" -X POST -d '{$JSON}' $URL  

It's espected to get response containing 2 fields:
body - containing expected object (or null)
errors - list of errors and infos, each containing type [ERRROR, INFO, WARNING]  
                                                   field - which field to blame  
                                                   message - error message                          

<b>To execute on Windows:</b>  
Get yourself SoapUI or stuff like that=) 

<b>API reference:</b> 

**To register customer:**   
URL: POST http://localhost:8080/customer/register  
params: 
firstName : String (Mandatory) cannot be longer than 16 symbols,
                               should contain only latin letters, spaces and symbols ' and -  
lastName : String (Mandatory) cannot be longer than 16 symbols,
                               should contain only latin letters, spaces and symbols ' and -  
email : String (Mandatory), should be in valid email format  

Except of above mentioned requirements following validation rules are applied: 
  - Cannot register multiple customers with the same email 

**To import loan:**   
URL: POST http://localhost:8080/loan/import  
params: 
id : Long (Mandatory) - provided by external loan issuing company  
mainAmount : Numeric (Mandatory) should be from 1 to 1000   
interestRate : Numeric (Mandatory), should be from 0 to 100  

Except of above mentioned requirements following validation rules are applied: 
  - Cannot import multiple loans with the same id
  
**To import payment:**   
URL: POST http://localhost:8080/payment/import  
params: 
id : Long (Mandatory) - provided by external loan issuing company  
loanId: Long (Mandatory) - provided by external loan issuing company
mainAmount : Numeric (Mandatory) should be from 1 to 1000   
interestAmount : Numeric (Mandatory), should be from 0 to 1000  

Except of above mentioned requirements following validation rules are applied: 
  - Cannot import multiple payments with the same id
  - Cannot import payment for unexisting loan
  

**To expose available loans for investment:**   
URL: GET http://localhost:8080/loan/list-investable 
Availability calculated based on main amount, payments and investments being done

**To invest into loans:**   
URL: POST http://localhost:8080/customer/invest  
params: 
loanId: Long (Mandatory)
customerId: Long (Mandatory)
amount : Numeric (Mandatory) should be from 1 to 1000    

Except of above mentioned requirements following validation rules are applied: 
  - Cannot invest in unexisting loan
  - Cannot invest for unexisting customer
  - Cannot invest in loans not being available for investment
  - If investment amount is bigger than amount available for 
        investment - only this available sum will be investment
           (assuming in real system remaining amount to return to customer's account)
