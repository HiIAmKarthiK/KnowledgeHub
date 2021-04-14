import { Component, OnInit } from '@angular/core';
import { DictionaryService } from 'src/app/services/dictionary.service';

@Component({
  selector: 'app-dictionary',
  templateUrl: './dictionary.component.html',
  styleUrls: ['./dictionary.component.css']
})
export class DictionaryComponent implements OnInit {

  constructor(private dictionary:DictionaryService) { }

  ngOnInit(): void {
  }

  resultMeaning:any='';
  errorpage:boolean=false;
  searchbox:boolean=true;

  searchword:any;
  searchmeaning(){
    // console.log(this.searchword)
    this.dictionary.getmeaning(this.searchword).toPromise().then(data=>{
      this.setresultarray(data);
      console.log(data);
      this.searchbox=false;
    }, err =>{
      this.errorpage=true 
    })
  }

  setresultarray(data:any){
    this.resultMeaning=data;
    // console.log(this.resultMeaning)
    // console.error();
  }
}
