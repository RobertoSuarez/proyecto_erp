/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.seguridad.controllers;

import com.inventario.DAO.Clases.RolesDAO;
import com.seguridad.dao.RolDAO;
import com.seguridad.dao.UsuarioDAO;
import com.seguridad.models.Rol;
import com.seguridad.models.Usuario;
import java.io.IOException;
import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.servlet.http.HttpSession;

@Named(value = "usuarioMB")
@SessionScoped
public class UsuarioController implements Serializable {

    private Usuario usuario;
    private List<Usuario> listaUsuario;
    private UsuarioDAO usuarioDAO;
    private Usuario selectionUser;
    private Usuario infoUser;
    String userName = "";
    private List<Rol> rolesUser;
    private List<Rol> generalRols;
    private RolDAO rDao;
    String warnMsj = "Advertencia";
    String infMsj = "Exito";
    private final FacesContext facesContext = FacesContext.getCurrentInstance();
    HttpSession httpSession = (HttpSession) facesContext.getExternalContext().getSession(true);
    RolDAO rolDao;
    RolesDAO rolesDAO;

    public UsuarioController() {
        usuario = new Usuario();
        this.rolesUser = new ArrayList<>();
        this.selectionUser = null;
        this.infoUser = null;
        usuarioDAO = new UsuarioDAO();
        rolDao = new RolDAO();
        rolesDAO = new RolesDAO();
        rDao = new RolDAO();
        listaUsuario = usuarioDAO.listUsers();
        System.out.println("########## Pasa algo");
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public RolesDAO getRolesDAO() {
        return rolesDAO;
    }

    public void setRolesDAO(RolesDAO rolesDAO) {
        this.rolesDAO = rolesDAO;
    }

    public boolean rolExist(String rol) {
        return rolesDAO.rolExist(rol);
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public UsuarioDAO getUsuarioDAO() {
        return usuarioDAO;
    }

    public void setUsuarioDAO(UsuarioDAO usuarioDAO) {
        this.usuarioDAO = usuarioDAO;
    }

    public List<Usuario> getListaUsuario() {
        return listaUsuario;
    }

    public void setListaUsuario(List<Usuario> listaUsuario) {
        this.listaUsuario = listaUsuario;
    }

    public Usuario getSelectionUser() {
        return selectionUser;
    }

    public void setSelectionUser(Usuario selectionUser) {
        this.selectionUser = selectionUser;
    }

    public Usuario getInfoUser() {
        return infoUser;
    }

    public void setInfoUser(Usuario infoUser) {
        this.infoUser = infoUser;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public List<Rol> getRolesUser() {
        return rolesUser;
    }

    public void setRolesUser(List<Rol> rolesUser) {
        this.rolesUser = rolesUser;
    }

    public List<Rol> getGeneralRols() {
        return generalRols;
    }

    public void setGeneralRols(List<Rol> generalRols) {
        this.generalRols = generalRols;
    }

    public void registrarUsuario() throws Exception {

        Pattern pattern = Pattern
                .compile("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                        + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");
        Matcher matcher = pattern.matcher(usuario.getEmail());  ///Ingresar dato Email a la BD
        //  
        try {

            if ("".equals(usuario.getNombre())) {
                mensajeDeAdvertencia("Ingrese un Nombre");

                //  PrimeFaces.current().ajax().update("form:messages");
            } else if ("".equals(usuario.getApellido())) {
                mensajeDeAdvertencia("Ingrese un Apellido");

            } else if ("".equals(usuario.getUsername())) {
                mensajeDeAdvertencia("Ingrese un Usuario");

            } else if ("".equals(usuario.getPassword())) {
                mensajeDeAdvertencia("Ingrese una Contraseña");
            }/* else if (usuario.isHabilitado() == false) {
                mensajeDeAdvertencia("Aceptar los terminos y condiciones");

            } */ else if (matcher.find() == false) {
                mensajeDeAdvertencia("Ingrese un email válido");
            } else {
                this.usuarioDAO.registrarUsuario(usuario);
                mensajeDeExito("Usuario registrado");

            }
        } catch (Exception e) {
            e.getMessage();
        }
    }

    public void iniciarSesion() throws IOException, SQLException {
        Usuario usuarioSesion = new Usuario();
        if ("".equals(usuario.getUsername())) {
            mensajeDeAdvertencia("Ingrese un usuario");
        }
        if ("".equals(usuario.getPassword())) {
            mensajeDeAdvertencia("Ingrese una contraseña");
        }
        if (!usuario.getUsername().isEmpty() && !usuario.getPassword().isEmpty()) {
            usuarioSesion = usuarioDAO.iniciarSesion(usuario);
            System.out.println(usuarioSesion.getApellido());
            if (usuarioSesion != null) {
                if (usuarioSesion.getCodigoAux() < 1) {
                    mensajeDeAdvertencia(usuarioSesion.getMensajeAux());

                } else {
                    mensajeDeExito(usuarioSesion.getMensajeAux());

                    usuario = usuarioSesion;
                    List<Rol> rolesSesion = rolDao.
                            getRolesByUsers(usuarioSesion.getIdUsuario());
                    rolesDAO = new RolesDAO(rolesSesion);
                    //Registrar usuario en HttpSession
                    httpSession.setAttribute("username", usuarioSesion);

                    //Registrar usuario en Session de JSF
                    FacesContext.getCurrentInstance().getExternalContext()
                            .getSessionMap().put("usuario", usuarioSesion);
                    FacesContext.getCurrentInstance().getExternalContext()
                            .getSessionMap().put("roles", rolesSesion);
                    FacesContext.getCurrentInstance().getExternalContext().
                            getSessionMap().put("usuario_rol",
                                    usuarioDAO.rolRRHH(
                                            usuarioSesion.getIdUsuario()));
                    facesContext.getExternalContext()
                            .redirect("../Global/Main.xhtml");

                }
            } else {
                mensajeDeAdvertencia("Error de conexión al intentar iniciar sesión.");
            }
        }
    }

    public void cerrarSession() throws IOException {
        System.out.println(httpSession.getAttribute(
                "usuario") + "Holas CESION");
        httpSession.removeAttribute("usuario");
        System.out.println(httpSession.getAttribute(
                "usuario") + "Holas CESION");
        facesContext.getExternalContext().redirect(
                "../login_and_registro/login.xhtml");
        usuario.setPassword("");
        usuario.setUsername("");
        System.out.println(httpSession.getAttribute(
                "usuario") + "Holas CESION");
    }

    public void mensajeDeAdvertencia(String msj) {
        FacesContext.getCurrentInstance().
                addMessage(null, new FacesMessage(
                        FacesMessage.SEVERITY_WARN, warnMsj, msj));

    }

    public void mensajeDeExito(String msj) {
        FacesContext.getCurrentInstance().
                addMessage(null, new FacesMessage(
                        FacesMessage.SEVERITY_INFO, infMsj, msj));

    }

    //Controla que en caso de que no haya un usuario conectado entonces redirija al incio.
    public void verificarInicioSesion() {
        FacesContext context = FacesContext.getCurrentInstance();
        Usuario usuarioSesion = (Usuario) context
                .getExternalContext().getSessionMap().get("usuario");
        try {
            if (usuarioSesion == null || usuarioSesion.getIdUsuario() < 1) {
                context.getExternalContext()
                        .redirect("../login_and_registro/login.xhtml");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //Verifica si la sesion tiene un usuario, devuelve un booleano
    public boolean verificarSesion() {
        Usuario user = new Usuario();
        try {
            user = (Usuario) FacesContext.getCurrentInstance()
                    .getExternalContext().getSessionMap().get("usuario");
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (user != null && user.getIdUsuario() > 0) {
            return true;
        }
        return false;
    }

    //Verifica si el usuario en sesion cuenta con los roles permitidos (nombre), devuelve un booleano
    public boolean verificarPermisoNombre(List<String> rolesPermitidos, int codigoModulo) {
        try {
            List<Rol> rolesSesion = (List<Rol>) FacesContext
                    .getCurrentInstance().getExternalContext()
                    .getSessionMap().get("roles");
            for (String rolPermitido : rolesPermitidos) {
                for (Rol rolCompare : rolesSesion) {
                    if (rolPermitido.equals(rolCompare.getNombre())) {
                        return true;
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    //Verifica si el usuario en sesion cuenta con los roles permitidos (código), devuelve un booleano
    public boolean verificarPermisoCodigo(List<Integer> rolesPermitidos, int codigoModulo) {
        try {
            List<Rol> rolesSesion = (List<Rol>) FacesContext
                    .getCurrentInstance().getExternalContext()
                    .getSessionMap().get("roles");
            for (int rolPermitido : rolesPermitidos) {
                for (Rol rolCompare : rolesSesion) {
                    if (rolPermitido == rolCompare.getId()) {
                        return true;
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public void chargeUser(Usuario seleccion) {
        System.out.println(seleccion.getUsername());
        this.infoUser = seleccion;
        this.userName = seleccion.getUsername();
        this.rolesUser = this.rDao.getRolesByUsers(seleccion.getIdUsuario());
    }

    public void chargeRols() {
        this.generalRols = this.rDao.GetRolsDifferentOfUser(this.infoUser.getIdUsuario());
    }

    public void addRolsForUser(Rol rolAux) {
        this.rDao.addRolUser(rolAux.getId(), this.infoUser.getIdUsuario());
        FacesMessage messages = new FacesMessage(FacesMessage.SEVERITY_INFO, "Éxito", "Nuevo rol ingresado");
        FacesContext.getCurrentInstance().addMessage(null, messages);
    }

    public void deleteRolsForUser(Rol rolAux) {
        this.rDao.deleteRolUser(rolAux.getId(), this.infoUser.getIdUsuario());
        FacesMessage messages = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Éxito", "Nuevo rol ingresado");
        FacesContext.getCurrentInstance().addMessage(null, messages);
    }
}
