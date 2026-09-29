document.addEventListener('DOMContentLoaded',()=>{
    
    document.querySelector('#home').style.visibility ='visible'

    // HOME SECTIOM
    let homebtn =document.querySelector('#homebtn')
    homebtn.addEventListener('click',()=>{
        
        document.querySelectorAll('.sec').forEach((elem) => {
            
            elem.style.visibility ='hidden' 
        })
        document.querySelector('#home').style.visibility ='visible'
        
        }) 

        //VIEW SECTION
    let viewbtn =document.querySelector('#viewbtn')
    viewbtn.addEventListener('click',()=>{
        
        
        document.querySelectorAll('.sec').forEach((elem) => {
            elem.style.visibility ='hidden' 
            
        })
    
        document.querySelector('#view').style.visibility ='visible'
        
        
        })   


    //REQUEST SECTION
    let reqbtn= document.querySelector('#reqbtn')
    reqbtn.addEventListener('click',()=>{
        console.log('hello')
        document.querySelectorAll('.sec').forEach((elem) => {
            elem.style.visibility ='hidden'
            console.log('first') 
        })

        document.querySelector('#request').style.visibility ='visible'
        console.log('last')
    })

    //LOGOUT SECTION
    let logbtn= document.querySelector('#logbtn')
    logbtn.addEventListener('click',()=>{
        document.querySelectorAll('.sec').forEach((elem) => {
                elem.style.visibility ='hidden' 
        })
            
        document.querySelector('#log').style.visibility ='visible';
        if(confirm('do you want to log out?')){
            window.location.href="../php/student.php"
        }
        else{
            
        }
                         
    })
    
    
})