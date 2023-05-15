import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import {UsuariosComponent} from './components/usuarios/usuarios.component';
import { LoginComponent } from './components/usuarios/login.component';
import { LoggedComponent } from './components/usuarios/logged.component';
import { UsuariosFormComponent } from './components/usuarios/usuarios-form.component';

const routes: Routes = [
  {path: '', pathMatch: 'full', redirectTo: 'usuarios'},
  {path: 'usuarios', component: UsuariosComponent},
  {path: 'login', component: LoginComponent},
  {path: 'logged', component: LoggedComponent},
  {path: 'usuarios/form', component: UsuariosFormComponent},
  {path: 'usuarios/form/:id', component: UsuariosFormComponent},
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
