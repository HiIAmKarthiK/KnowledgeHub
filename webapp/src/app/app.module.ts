import { ConfirmEqualValidatorDirective } from './shared/confirm-equal-validator.directive';
import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { PdfViewerModule } from 'ng2-pdf-viewer';
import { FormsModule } from '@angular/forms';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { LandingComponent } from './components/landing/landing.component';
import { UploadingbookComponent } from './components/uploadingbook/uploadingbook.component';

import { HttpClientModule } from '@angular/common/http';
import { BookReaderComponent } from './components/book-reader/book-reader.component';
import { LoginComponent } from './components/login/login.component';
import { ReactiveFormsModule } from '@angular/forms';

import { RegistrationComponent } from './components/registration/registration.component';
import { DashboardComponent } from './components/dashboard/dashboard.component';

import { MatIconModule } from '@angular/material/icon';
import { MatButtonModule } from '@angular/material/button';
import { MatSelectModule } from '@angular/material/select';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { BookProfileComponent } from './components/book-profile/book-profile.component';
import { MatSliderModule } from '@angular/material/slider';
// import {MatSnackBarModule} from '@angular/material/snack-bar';
import { MatToolbarModule } from '@angular/material/toolbar';
import { MatTooltipModule } from '@angular/material/tooltip';
import { ViewNotesComponent } from './components/view-notes/view-notes.component';
import { MatSidenavModule } from '@angular/material/sidenav';
import { MatDialogModule } from '@angular/material/dialog';
import { DictionaryComponent } from './components/dictionary/dictionary.component';
import { MatChipsModule } from '@angular/material/chips';
import { FilterPipe } from './pipes/filter.pipe';
import { MatExpansionModule } from '@angular/material/expansion';
import { UserProfileComponent } from './components/user-profile/user-profile.component';
import { MatMenuModule } from '@angular/material/menu';
import { FavouritesComponent } from './components/favourites/favourites.component';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { SpeechToTextComponent } from './components/speech-to-text/speech-to-text.component';
import { SpeechDialogComponent } from './components/speech-dialog/speech-dialog.component';
import { FlexLayoutModule } from '@angular/flex-layout';
import { MatProgressBarModule } from '@angular/material/progress-bar';
import { TextSpeechComponent } from './components/text-speech/text-speech.component';
import { PaymentDialogComponent } from './components/payment-dialog/payment-dialog.component';

@NgModule({
  declarations: [
    AppComponent,
    LandingComponent,
    UploadingbookComponent,
    LoginComponent,
    RegistrationComponent,
    BookReaderComponent,
    DashboardComponent,
    BookProfileComponent,
    ViewNotesComponent,
    DictionaryComponent,
    FilterPipe,
    ConfirmEqualValidatorDirective,
    UserProfileComponent,
    FavouritesComponent,
    SpeechToTextComponent,
    SpeechDialogComponent,
    TextSpeechComponent,
    PaymentDialogComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    BrowserAnimationsModule,
    PdfViewerModule,
    ReactiveFormsModule,
    FormsModule,
    HttpClientModule,
    MatFormFieldModule,
    MatButtonModule,
    MatSliderModule,
    MatIconModule,
    MatInputModule,
    MatSelectModule,
    BrowserAnimationsModule,
    // MatSnackBarModule
    MatToolbarModule,
    MatTooltipModule,
    MatSidenavModule,
    MatDialogModule,
    MatChipsModule,
    MatExpansionModule,
    MatMenuModule,
    NgbModule,
    FlexLayoutModule,
    MatProgressBarModule,
  ],
  providers: [],
  bootstrap: [AppComponent],
})
export class AppModule {}
