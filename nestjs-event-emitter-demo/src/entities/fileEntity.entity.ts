export class FileEntity {
    public id: number;
    public fileName: string;
    public fileUrl: string;
    public key: string;

    constructor(fileName: string, fileUrl: string, key: string){
        this.fileName = fileName;
        this.fileUrl = fileUrl;
        this.key = key;
    }
    
}