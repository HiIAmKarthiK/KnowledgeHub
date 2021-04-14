import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root',
})
export class SharedService {
  userEmail: string;
  userName: string;
  profilepic: any;
  role: any;
  constructor() {}
  setMessage(data) {
    this.userEmail = data;
  }

  setRole(userRole) {
    this.role = userRole;
  }
  getMessage() {
    return this.userEmail;
  }

  getRole() {
    return this.role;
  }
  getUserName() {
    return this.userName;
  }
  setUserName(userName) {
    this.userName = userName;
  }

  getUserpic() {
    return this.profilepic;
  }
  setUserpic(profilepic) {
    this.profilepic = profilepic;
  }
}
