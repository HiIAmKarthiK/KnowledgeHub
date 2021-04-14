import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root',
})
export class FavouritesService {
  constructor(private http: HttpClient) {}

  getAllFavouriteBooks(email: string) {
    return this.http.get(
      `/api/v1/recommendation/favourites?emailId=${email}`
    );
  }

  addToFavourites(email, bookTitle) {
    return this.http.post(
      `/api/v1/recommendation/favourite?emailId=${email}&bookTitle=${bookTitle}`,
      bookTitle,
      { responseType: 'text' }
    );
  }

  checkIfBookIsFavourite(email, bookTitle) {
    return this.http.get(
      `/api/v1/recommendation/userfavourite?emailId=${email}&bookTitle=${bookTitle}`,
      { responseType: 'text' }
    );
  }

  unFavouriteBook(email: string, bookTitle: string) {
    return this.http.get(
      `/api/v1/recommendation/unfavourite?emailId=${email}&bookTitle=${bookTitle}`,
      { responseType: 'text' }
    );
  }
}
