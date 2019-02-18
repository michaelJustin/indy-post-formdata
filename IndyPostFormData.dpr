program IndyPostFormData;

{$APPTYPE CONSOLE}

uses
  IdHTTP, IdMultipartFormData, SysUtils;

const
  URL = 'http://localhost:8080/indy-post-formdata-1.0-SNAPSHOT/webresources/generic/pdf';

var
  Indy: TIdHTTP;
  Params: TIdMultiPartFormDataStream;
  Response: string;

begin
  Indy := TIdHTTP.Create;
  Params :=  TIdMultiPartFormDataStream.Create;
  try
    try
      Params.AddFile('file', 'client.pdf');
      Response := Indy.Post(URL, Params);
      WriteLn(Response);
    except
      on E:Exception do
        Writeln(E.Classname, ': ', E.Message);
    end;
  finally
    Params.Free;
    Indy.Free;
  end;
  ReadLn;
end.
