
package servidor;

import Datos.evaluadorDAO;
import sop_corba.interfazEvaluadorPOA;

public class evaluadorImp extends interfazEvaluadorPOA {

    @Override
    public boolean ingresarConceptoEvaluador(int idUsuario, int parCodigoAnteproyecto, int parConcepto) {
        System.out.println("igresando concepto al proyecto");
        evaluadorDAO obj = new evaluadorDAO();
        return obj.ingresarCocepto(idUsuario, parCodigoAnteproyecto, parConcepto);
        
    }

    
 
}
