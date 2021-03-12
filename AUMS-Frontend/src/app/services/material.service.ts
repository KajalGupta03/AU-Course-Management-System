import { Injectable } from '@angular/core';
import { HttpClient, HttpClientModule } from '@angular/common/http';
import { Observable } from 'rxjs';
import { material } from './../ApiResponse';
@Injectable({
  providedIn: 'root',
})
export class MaterialService {
  constructor(private http: HttpClient) {}


  addmaterial(materialObject: any): Observable<any> {
    console.log(materialObject.courseId);
    let materialinput = new FormData();
    materialinput.append('courseId', materialObject.courseId);
    materialinput.append(
      'materialDescription',
      materialObject.materialDescription
    );
    materialinput.append('fileType', materialObject.fileType);
    for(let file of materialObject.file)
    {
      materialinput.append('file', file);
    }
    console.log(materialinput.getAll('file'));
      console.log(materialObject);
    return this.http.post<any>(
      'http://localhost:8080/material/addMaterial',
      materialinput
    );
  }


  // updateMaterialByCourseId(materialObject: any): Observable<any> {
  //   let materialinput = new FormData();
  //   materialinput.append('courseId', materialObject.courseId);
  //   materialinput.append(
  //     'materialDescription',
  //     materialObject.materialDescription
  //   );
  //   materialinput.append('fileType', materialObject.fileType);
  //   materialinput.append('file', materialObject.file);
  //   console.log(materialinput.getAll('file'));
  //   return this.http.put<any>(
  //     'http://localhost:8080/material/updateMaterial',
  //     materialinput
  //   );
  // }


  updateByMaterialId(materialObject:any): Observable<any>{
    let materialinput = new FormData();
    materialinput.append('courseId', materialObject.courseId);
    materialinput.append('materialDescription',materialObject.materialDescription);
    materialinput.append('fileType', materialObject.fileType);

    // materialinput.append('file', materialObject.file);
    for(let file of materialObject.file)
    {
      materialinput.append('file', file);
    }

    materialinput.append('previousMaterialId', materialObject.previousMaterialId);
    console.log(materialinput);
    return this.http.put<any>('http://localhost:8080/material/updateMaterial',materialinput)
  }

  getAllMaterial(key: any): Observable<any> {
    return this.http.get(
      'http://localhost:8080/material/getAllMaterial/' + key
    );
  }

  getLatestMaterialByCourseId(key: any): Observable<any> {
    return this.http.get(
      'http://localhost:8080/material/getLatestMaterial/' + key
    );
  }

  showPrevious(key1:any, key2:any):Observable<any>{
    return this.http.get(
      'http://localhost:8080/material/getAllMaterial/' + key1 +'/'+key2
    );
  }
}
