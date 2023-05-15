import { Component, OnInit } from '@angular/core';
import { Usuario } from 'src/app/models/usuario';
import swal from 'sweetalert2';
import { UsuarioService } from 'src/app/services/usuario.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-usuarios',
  templateUrl: './usuarios.component.html'
})
export class UsuariosComponent implements OnInit{

  titulo = 'Listado de Usuarios'
  usuarios: Usuario[];
  usuario: Usuario;

  constructor(private service: UsuarioService, private router: Router) {    
    this.titulo = 'Listado de Usuarios';
  }

ngOnInit(){  
  this.service.findAllUsuarios().subscribe(usuarios => this.usuarios = usuarios); 

}

}
