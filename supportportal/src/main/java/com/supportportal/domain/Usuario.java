package com.supportportal.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

@Entity
@Table(name="usuarios")
public class Usuario {
	
	
	@Id 
	@NotEmpty
	private String login;
	@NotEmpty
	private String password;
	@NotEmpty
	private String nombre;
    
	private Float cliente;
	@NotEmpty
	@Email
	private String email;
    
	@Column(name = "fechaalta")
	@Temporal(TemporalType.TIMESTAMP)
    private Date fechaAlta;    
	
	@PrePersist
	public void prePersist() {
		this.fechaAlta = new Date();
	}	
    
	@Column(name = "fechabaja")
    
	private Date fechaBaja;  
    
	private String status;
    
	private Float intentos;
	
    @Column(name = "fecharevocado")
    private Date fechaRevocado;
 
    @Column(name = "fecha_vigencia")
    private Date fechaVigencia;
    
    @Column(name = "no_acceso")
    private Integer noAcceso;
    @NotEmpty
    @Column(name = "apellido_paterno")
    private String apellidoPaterno;
    @NotEmpty
    @Column(name = "apellido_materno")
    private String apellidoMaterno;
    
    private Integer area;
        
    @Column(name = "fechamodificacion")
    private Date fechaModificacion;
    
    
	public Usuario() {
	}
	
	public Usuario(String login, String password, String nombre, Float cliente, String email, Date fechaAlta,
			Date fechaBaja, String status, Float intentos, Date fechaRevocado, Date fechaVigencia, Integer noAcceso,
			String apellidoPaterno, String apellidoMaterno, Integer area, Date fechaModificacion) {
		super();
		this.login = login;
		this.password = password;
		this.nombre = nombre;
		this.cliente = cliente;
		this.email = email;
		this.fechaAlta = fechaAlta;
		this.fechaBaja = fechaBaja;
		this.status = status;
		this.intentos = intentos;
		this.fechaRevocado = fechaRevocado;
		this.fechaVigencia = fechaVigencia;
		this.noAcceso = noAcceso;
		this.apellidoPaterno = apellidoPaterno;
		this.apellidoMaterno = apellidoMaterno;
		this.area = area;
		this.fechaModificacion = fechaModificacion;
	}



	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public Float getCliente() {
		return cliente;
	}

	public void setCliente(Float cliente) {
		this.cliente = cliente;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Date getFechaAlta() {
		return fechaAlta;
	}

	public void setFechaAlta(Date fechaAlta) {
		this.fechaAlta = fechaAlta;
	}

	public Date getFechaBaja() {
		return fechaBaja;
	}

	public void setFechaBaja(Date fechaBaja) {
		this.fechaBaja = fechaBaja;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Float getIntentos() {
		return intentos;
	}

	public void setIntentos(Float intentos) {
		this.intentos = intentos;
	}

	public Date getFechaRevocado() {
		return fechaRevocado;
	}

	public void setFechaRevocado(Date fechaRevocado) {
		this.fechaRevocado = fechaRevocado;
	}

	public Date getFechaVigencia() {
		return fechaVigencia;
	}

	public void setFechaVigencia(Date fechaVigencia) {
		this.fechaVigencia = fechaVigencia;
	}

	public Integer getNoAcceso() {
		return noAcceso;
	}

	public void setNoAcceso(Integer noAcceso) {
		this.noAcceso = noAcceso;
	}

	public String getApellidoPaterno() {
		return apellidoPaterno;
	}

	public void setApellidoPaterno(String apellidoPaterno) {
		this.apellidoPaterno = apellidoPaterno;
	}

	public String getApellidoMaterno() {
		return apellidoMaterno;
	}

	public void setApellidoMaterno(String apellidoMaterno) {
		this.apellidoMaterno = apellidoMaterno;
	}

	public Integer getArea() {
		return area;
	}

	public void setArea(Integer area) {
		this.area = area;
	}

	public Date getFechaModificacion() {
		return fechaModificacion;
	}

	public void setFechaModificacion(Date fechaModificacion) {
		this.fechaModificacion = fechaModificacion;
	}

	@Override
	public String toString() {
		return "Usuario [login=" + login + ", password=" + password + ", nombre=" + nombre + ", cliente=" + cliente
				+ ", email=" + email + ", fechaAlta=" + fechaAlta + ", fechaBaja=" + fechaBaja + ", status=" + status
				+ ", intentos=" + intentos + ", fechaRevocado=" + fechaRevocado + ", fechaVigencia=" + fechaVigencia
				+ ", noAcceso=" + noAcceso + ", apellidoPaterno=" + apellidoPaterno + ", apellidoMaterno="
				+ apellidoMaterno + ", area=" + area + ", fechaModificacion=" + fechaModificacion + "]";
	}
    
    

}



