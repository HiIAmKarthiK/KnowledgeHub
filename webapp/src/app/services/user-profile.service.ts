import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root',
})
export class UserProfileService {
  constructor(private httpClient: HttpClient) {}

  getuser(email: any) {
    return this.httpClient.get(
      `/api/v1/register/userdetails/${email}`
    );
  }
}
