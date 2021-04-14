import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root',
})
export class BookPathServicesService {
  constructor(private httpclient: HttpClient) {}

  getBookPath(bookTitle) {
    return this.httpclient.get(
      `/api/v1/book/bookdetails/${bookTitle}`,
     
    );
  }
  updateNotes(userEmail: any, previousRead) {
    console.log(previousRead);
    return this.httpclient.put(
      `/api/v1/register/uploadnotes/${userEmail}`,
      previousRead
    );
  }

  updateLastPage(userEmail: any, previousRead) {
    return this.httpclient.put(`/api/v1/register/user/previousRead/${userEmail}`, previousRead);
  }

  getAllNotes(userEmail: any) {
    return this.httpclient.get(
      `/api/v1/register/allnotes/${userEmail}`
    );
  }

  getLastPageRead(userEmail, bookTitle) {
    return this.httpclient.get(`/api/v1/register/lastPage/${userEmail}/${bookTitle}`)
  }
}
