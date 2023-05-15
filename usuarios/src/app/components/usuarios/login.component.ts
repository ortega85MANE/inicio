import { Component } from '@angular/core';
import { Usuario } from 'src/app/models/usuario';
import swal from 'sweetalert2';
import { UsuarioService } from 'src/app/services/usuario.service';
import { Router } from '@angular/router';
import { HeaderType } from 'src/app/enum/header-type';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html'
})
export class LoginComponent {

  titulo: string = 'Por favor Sign In!';
  usuario: Usuario;

  constructor(private service: UsuarioService, private router: Router) {
    this.usuario = new Usuario();
  }

  ngOnInit() {    
    
  }

  login(): void {
    console.log(this.usuario);
    if (this.usuario.login == null || this.usuario.password == null) {
      swal.fire('Error Login', 'Username o password vacías!', 'error');
      return;
    }
    this.service.login(this.usuario).subscribe(response => {
      response.headers
        .keys()
        .forEach(keyName =>
          console.log(
            `The value of the ${keyName} header is: ${response.headers.get(
              keyName
            )}`
          )
        );
      console.log(response.headers.get('Jwt-Token'));
      const token = response.headers.get('HeaderType.JWT_TOKEN');
          this.service.saveToken(token);
          this.service.addUserToLocalCache(response.body);
      this.router.navigate(['/logged']);
      swal.fire('Login', `Hola ${this.usuario.login}, has iniciado sesión con éxito!`, 'success');
});
  }
}
