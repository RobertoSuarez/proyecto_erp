
package com.recursoshumanos.Controller;

import com.seguridad.dao.UsuarioDAO;
import com.seguridad.models.Usuario;
import java.io.Serializable;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.servlet.http.HttpSession;

/**
 *
 * @author ninat
 */
@ManagedBean(name = "rolMB")
@ViewScoped
public class Roles implements Serializable {
    
    private UsuarioDAO usuarioDAO;

    public Roles() {
        usuarioDAO = new UsuarioDAO();
    }
    
    public String rol(){
        int rolRRHH;
        FacesContext context = FacesContext.getCurrentInstance();
        Usuario usuarioSesion = (Usuario) context.getExternalContext().getSessionMap().get("usuario");
        rolRRHH = usuarioDAO.rolRRHH(usuarioSesion.getIdUsuario());
        if(rolRRHH == 11)
            return "true";
        else
            return "false";
    }
    
}
