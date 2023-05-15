import { Component, OnInit, ViewChild } from '@angular/core';
import swal from 'sweetalert2';
import { UsuarioService } from 'src/app/services/usuario.service';
import { Usuario } from 'src/app/models/usuario';
import { Router } from '@angular/router';
import { MatPaginator, PageEvent } from '@angular/material/paginator';

@Component({
  selector: 'app-logged',
  templateUrl: './logged.component.html'
})
export class LoggedComponent {

  titulo = 'Listado de Usuarios'
  usuarios: Usuario[];
  usuario: Usuario;
  error: any;
  totalRegistros = 0;
  paginaActual = 0;
  totalPorPagina = 5;
  pageSizeOptions: number[] = [5, 10, 25, 100];

  @ViewChild(MatPaginator) paginator: MatPaginator;

  constructor(private service: UsuarioService, private router: Router) {    
    this.titulo = 'Listado de Usuarios';    
  }

ngOnInit(){  
  this.service.findAllUsuarios().subscribe(usuarios => this.usuarios = usuarios);
  this.calcularRangos();
}

paginar(event: PageEvent): void{
  this.paginaActual = event.pageIndex;
  this.totalPorPagina = event.pageSize;
  this.calcularRangos();

}

calcularRangos(){
  this.service.listarPaginas(this.paginaActual.toString(), this.totalPorPagina.toString())
  .subscribe(p => 
    {
      this.usuarios = p.content as Usuario[];
      this.totalRegistros = p.totalElements as number;
      this.paginator._intl.itemsPerPageLabel = "Items por pagina";
    });
}

refrescar(): void {
this.service.findAllUsuarios().subscribe(usuarios => this.usuarios = usuarios);    
}

activos(): void {
  this.service.activos().subscribe(usuarios => this.usuarios = usuarios);    
}

inactivos(): void {
  this.service.inactivos().subscribe(usuarios => this.usuarios = usuarios);    
}

revocados(): void {
  this.service.revocados().subscribe(usuarios => this.usuarios = usuarios);    
}

searchUsers(valor: string): void {
  this.service.search(valor).subscribe(usuarios => this.usuarios = usuarios);
}

ver(valor: string): void{
  this.service.ver(valor).subscribe(usuario => {
      this.usuario = usuario     
      console.log("VALOR DEL USUARIO DEVUELTO" + usuario);
      this.router.navigate(['/usuarios/form']);
    })
}

public eliminar(usuario: Usuario): void{

  swal.fire({
    title: 'Cuidado:',
    text: `¿Seguro que desea eliminar a ${usuario.nombre} ?`,
    icon: 'warning',
    showCancelButton: true,
    confirmButtonColor: '#3085d6',
    cancelButtonColor: '#d33',
    confirmButtonText: 'Si, eliminar!'
  }).then((result) => {
    if (result.value) {
      this.service.eliminar(usuario.login).subscribe(() => {
        //this.usuarios = this.usuarios.filter(a => a !== usuario);
        this.calcularRangos();
        swal.fire('Eliminado:', `Usuario ${usuario.nombre} eliminado con éxito`, 'success');
      });
    }
  });

}

}
