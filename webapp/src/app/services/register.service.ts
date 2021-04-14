import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root',
})
export class RegisterService {
  constructor(private http: HttpClient) {}

  signup(user: any) {
    return this.http.post('/api/v1/register/user', user);
  }

  changeRoleOfUser(userEmail: any) {
    return this.http.put(
      `/api/v1/register/user/role/${userEmail}`,
      userEmail
    );
  }
}
