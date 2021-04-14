import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable, OnInit } from '@angular/core';

@Injectable({
  providedIn: 'root',
})
export class BookProfileServiceService {
  constructor(private httpClient: HttpClient) {}

  getbookdetails(bookTitle) {
    return this.httpClient.get(
      `/api/v1/book/bookdetails/${bookTitle}`
    );
  }

  addCommentsToBooks(bookTitle: any, commentDetails: any) {
    return this.httpClient.put(
      `/api/v1/book/addcomments/${bookTitle}`,
      commentDetails
    );
  }

  startReadingBook(email, bookTitle) {
    return this.httpClient.post(
      `/api/v1/recommendation/startreading?emailId=${email}&bookTitle=${bookTitle}`,
      bookTitle,
      { responseType: 'text' }
    );
  }

  downloadFile(bookTitle): any {
    return this.httpClient.get(
      `/api/v1/book/download/${bookTitle}`,
      { responseType: 'blob' }
    );
  }
}
