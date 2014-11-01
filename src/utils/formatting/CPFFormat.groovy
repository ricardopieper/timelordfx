
package utils.formatting


/**
 *
 * @author Ricardo
 */
public class CPFFormat implements IMask {
   
    
    public String putMask(String cpf){
       
        if (cpf == null || cpf.size() < 11){ 
            return getEmpty()
        }
        else{
            MaskFunctions.formatString(sanitize(cpf), getMask())
        }
    }
    
    public String sanitize(String cpf){
        cpf.replace(".","").replace("-","").replace(" ",'')
    }
    
   public String getMask(){
        "999.999.999-99"
   }
    
    public String getEmpty(){
        getMask().replace('9',' ')
    }
}
