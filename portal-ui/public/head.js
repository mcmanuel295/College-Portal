
document.addEventListener('DOMContentLoaded',function(){ 
    
    document.querySelectorAll('.joined').forEach(
        (elem)=>{

            elem.onmouseenter=()=>{
                document.querySelector('ul').style.backgroundColor='rgb(238, 108, 130)';
            
                document.querySelectorAll('.loginMenu').forEach ( 
                    (button) =>{ 
                        button.style.visibility ="visible"
                    })
                }
        }
    )

    document.querySelector('ul').onmouseleave =()=>{
        document.querySelectorAll('.loginMenu').forEach((elem)=>{
            elem.querySelector('.loginMenu').style.visibility='hidden'

            document.querySelector('ul').style.backgroundColor=' ';
        })

    }
        
    
         
})







