
package utils.formatting

/**
 *
 * @author Ricardo
 */
public class CEPFormat implements IMask {
   
      
    public String putMask(String cep){
       
        if (cep == null || cep.size() < 8){ 
            return getEmpty()
        }
        else{
            
            MaskFunctions.formatString(sanitize(cep), getMask())

        }
    }
    
    public String sanitize(String cep){
        cep.replace("-","").replace(" ",'')
    }
    
   public String getMask(){
        "99999-999"
   }
    
    public String getEmpty(){
        getMask().replace('9',' ')
    }
}
