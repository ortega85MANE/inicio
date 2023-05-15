import { Component, OnInit } from '@angular/core';
import swal from 'sweetalert2'
import { Usuario } from 'src/app/models/usuario';
import { UsuarioService } from 'src/app/services/usuario.service';
import { Router, ActivatedRoute } from '@angular/router';

@Component({
  selector: 'app-usuarios-form',
  templateUrl: './usuarios-form.component.html',
  styleUrls: ['./usuarios-form.component.css']
})
export class UsuariosFormComponent implements OnInit {

  titulo = 'Crear Usuarios';

  usuario: Usuario = new Usuario();

  error: any;

  constructor(private service: UsuarioService, 
              private router: Router,
              private route: ActivatedRoute) { }

  ngOnInit() {
    this.route.paramMap.subscribe(params => {
      const id: string = params.get('id');
      if(id){
        this.service.ver(id).subscribe(usuario => this.usuario = usuario)
      }
    })
  }

  public crear(): void {
    this.service.crear(this.usuario).subscribe(usuario => {
      console.log(usuario);
      swal.fire('Nuevo:', `Usuario ${usuario.nombre } ${ usuario.apellidoPaterno} creado con éxito`, 'success');
      this.router.navigate(['/logged']);
    }, err => {
      if(err.status === 400){
        this.error = err.error;
        console.log(this.error);
      }
    });
  }

  editar(): void {
    this.service.editar(this.usuario).subscribe(usuario => {
      console.log(usuario);
      swal.fire('Modificado:', `Usuario ${usuario.nombre} actualizado con éxito`, 'success');
      this.router.navigate(['/logged']);
    }, err => {
      if(err.status === 400){
        this.error = err.error;
        console.log(this.error);
      }
    });
  }


}

