import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { DashboardService } from 'src/app/services/dashboard.service';
import { FavouritesService } from 'src/app/services/favourites.service';
import { Neo4jService } from 'src/app/services/neo4j/neo4j.service';

@Component({
  selector: 'app-favourites',
  templateUrl: './favourites.component.html',
  styleUrls: ['./favourites.component.css'],
})
export class FavouritesComponent implements OnInit {
  public favouriteBooks: any;
  public neoBooks: any;
  public mBooks: any;

  constructor(
    private favouriteService: FavouritesService,
    private router: Router,
    private neo4jService: Neo4jService,
    private dashboardService: DashboardService
  ) {}

  ngOnInit(): void {
    this.getAllFavouriteBooks();
    this.getAllBooksFromNeo();
    this.getAllBooksFromMongo();
  }

  getAllFavouriteBooks() {
    let email = localStorage.getItem('userEmail');
    this.favouriteService.getAllFavouriteBooks(email).subscribe((data) => {
      console.log(data);
      this.favouriteBooks = data;
    });
  }

  toBookProfile(book) {
    this.router.navigateByUrl(`bookprofile/${book.bookTitle}`);
    console.log(book);
  }

  getAllBooksFromMongo() {
    this.dashboardService.getAllBooks().subscribe((data) => {
      this.mBooks = data;
    });
  }

  getAllBooksFromNeo() {
    this.neo4jService.getAllBooks().subscribe((data) => {
      this.neoBooks = data;
      console.log(data);
    });
  }

  getBookViews(passedBook) {
    for (let book of this.neoBooks) {
      if (book.bookTitle == passedBook.bookTitle) {
        return book.totalViews;
      }
    }
  }

  getBookDownloads(passedBook) {
    for (let book of this.neoBooks) {
      if (book.bookTitle == passedBook.bookTitle) {
        return book.totalDownloads;
      }
    }
  }

  getBookImage(passedBook) {
    for (let book of this.mBooks) {
      if (book.bookTitle == passedBook.bookTitle) {
        return book.imageByte;
      }
    }
  }

  unFavourite(book) {
    let email = localStorage.getItem('userEmail');
    this.favouriteService
      .unFavouriteBook(email, book.bookTitle)
      .subscribe((data) => {
        console.log(data);
        this.getAllFavouriteBooks();
      });
  }
}
