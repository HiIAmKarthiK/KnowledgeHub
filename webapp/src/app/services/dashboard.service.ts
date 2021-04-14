import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root',
})
export class DashboardService {
  constructor(private http: HttpClient) {}
  booktitle: any;
  getAllBooks() {
    return this.http.get('/api/v1/book/books');
  }

  getBooksByGenre(genre: string) {
    return this.http.post('/api/v1/book/byGenre', genre);
  }

  getRecommendedBooks(email) {
    return this.http.get(
      `/api/v1/recommendation/recommendations?emailId=${email}`
    );
  }
}
