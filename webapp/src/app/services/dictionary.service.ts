import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class DictionaryService {

  constructor(private httpclient: HttpClient) { }

  getmeaning(word:any){
    return this.httpclient.get(
      `/api/v1/dictionary/meaning/${word}`,{ responseType: 'json' }
    )
  }
}
