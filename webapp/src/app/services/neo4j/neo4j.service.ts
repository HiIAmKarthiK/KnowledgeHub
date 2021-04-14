import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { BehaviorSubject } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class Neo4jService {
  constructor(private http: HttpClient) {}

  private emailSource = new BehaviorSubject<any>(
    localStorage.getItem('userEmail')
  );
  email = this.emailSource.asObservable();

  setEmail(email: any) {
    localStorage.setItem('userEmail', email);
    this.emailSource.next(email);
    console.log(email);
  }

  updateBookViews(bookTitle: string) {
    return this.http.put('/api/v1/recommendation/updateviews', bookTitle, {
      responseType: 'text',
    });
  }

  updateDownloads(bookTitle: string) {
    return this.http.put('/api/v1/recommendation/updatedownloads', bookTitle, {
      responseType: 'text',
    });
  }

  getAllBooks() {
    return this.http.get(`/api/v1/recommendation/books`);
  }

  getLastReadBooks(email: string) {
    return this.http.get(`/api/v1/recommendation/reading?emailId=${email}`);
  }

  getABook(bookTitle: string) {
    return this.http.get(`/api/v1/recommendation/abook?bookTitle=${bookTitle}`);
  }
}
