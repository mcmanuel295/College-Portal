document.addEventListener('DOMContentLoaded',()=>{
    
    document.querySelector('#home').style.visibility ='visible'
    
    // HOME SECTION
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


        // DONATE SECTION
    let donatebtn = document.querySelector('#donatebtn')
    donatebtn.addEventListener('click',()=>{
        document.querySelectorAll('.sec').forEach((elem )=> {
            elem.style.visibility ='hidden' 
        })

        document.querySelector('#donate').style.visibility ='visible'

    })

    //REQUEST SECTION
    let reqbtn= document.querySelector('#reqbtn')
    reqbtn.addEventListener('click',()=>{
        document.querySelectorAll('.sec').forEach((elem) => {
            elem.style.visibility ='hidden' 
        })

        document.querySelector('#request').style.visibility ='visible'
    })

    //LOGOUT SECTION
    let logbtn= document.querySelector('#logbtn')
    logbtn.addEventListener('click',()=>{
        document.querySelectorAll('.sec').forEach((elem) => {
                elem.style.visibility ='hidden' 
        })
            
        document.querySelector('#log').style.visibility ='visible'
        if(confirm('do you want to log out?')){
            document.location.href="./student.php"
        }
        else{
        }
    })
    

    let rad =document.getElementsByName('170805009').firstCHild.value = 'eret'
    alert(` the value of the radiobutton is :${rad}`)


    // function request(){
    //     alert('this is working!!! ')
    // }
})