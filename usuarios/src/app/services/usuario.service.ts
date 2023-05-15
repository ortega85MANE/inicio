import { HttpClient, HttpResponse, HttpHeaders, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Usuario } from '../models/usuario';
import { Observable, catchError, throwError } from 'rxjs';

import { Router } from '@angular/router';

@Injectable({
  providedIn: 'root'
})
export class UsuarioService {

  usuario: Usuario;
  private token: string;
  private activo: string = "A";
  private inactivo: string = "I";
  private revocado: string = "R";
  private cabeceras: HttpHeaders = new HttpHeaders({ 'Content-Type': 'application/json' });

  constructor(private http: HttpClient, private router: Router) { }

  findAllUsuarios(): Observable<Usuario[]>{
    return this.http.get<Usuario[]>(`http://localhost:8082/user/all`).pipe(
      catchError(e => {
      this.isNoAutorizado(e);
      return throwError(e)      
      })
    );
  }

  public listarPaginas(page: string, size: string): Observable<any>{
    const params = new HttpParams()
    .set('page', page)
    .set('size', size);
    return this.http.get<any>(`http://localhost:8082/pagina`, {params: params});
  }

  login(usuario: Usuario): Observable<HttpResponse<Usuario>> {
    return this.http.post<Usuario>(`http://localhost:8082/login`, usuario, { observe: 'response' });
  }

  search(term: string): Observable<Usuario[]>{
    return this.http.get<Usuario[]>(`http://localhost:8082/por-nombre/${term}`);      
  }

  activos(): Observable<Usuario[]>{
    console.log("ACTIVO "+ this.activo)
    return this.http.get<Usuario[]>(`http://localhost:8082/por-status/${this.activo}`);      
  }

  inactivos(): Observable<Usuario[]>{
    return this.http.get<Usuario[]>(`http://localhost:8082/por-status/${this.inactivo}`);     
  }

  revocados(): Observable<Usuario[]>{
    return this.http.get<Usuario[]>(`http://localhost:8082/por-status/${this.revocado}`);      
  }

    crear(usuario: Usuario): Observable<Usuario> {      
      return this.http.post<Usuario>(`http://localhost:8082/user/create`, usuario,
        { headers: this.cabeceras });
    }

    ver(valor: string): Observable<Usuario> {
      console.log("VALOR DE EDITAR"+ valor);
      return this.http.get<Usuario>(`http://localhost:8082/por-login/${valor}`);
    }

    editar(usuario: Usuario): Observable<Usuario> {
      return this.http.put<Usuario>(`http://localhost:8082/editar/${usuario.login}`, usuario,
        { headers: this.cabeceras });
    }

    eliminar(valor: string): Observable<void> {
      return this.http.delete<void>(`http://localhost:8082/remove/${valor}`);
    }

  saveToken(token: string): void {
    this.token = token;
    localStorage.setItem('token', token);
  }

  addUserToLocalCache(usuario: Usuario): void {
    localStorage.setItem('usuario', JSON.stringify(usuario));
  }

  getUserFromLocalCache(): Usuario {
    return JSON.parse(localStorage.getItem('usuario'));
  }

  loadToken(): void {
    this.token = localStorage.getItem('token');
  }

  getToken(): string {
    return this.token;
  }

  private isNoAutorizado(e): boolean {
    if (e.status == 401 || e.status == 403) {

      this.router.navigate(['/login']);
      return true;
    }

    return false;
  }


}
