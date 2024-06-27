
 import { useState } from "react";

 const CreadorClip: React.FC = () =>{
const [clipUrl, setClipUrl] = useState<string | null>(null);
  const [genero, setGenero] = useState<string>('');
  const [instrumento, setInstrumento] = useState<string>('');

  const handleGenerarClip = async () => {
    try {
      const response = await fetch("http://localhost:8000/crear_clip", {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
        },
        body: JSON.stringify({ genero, instrumento }),
      });
      
      if (!response.ok) {
        throw new Error("ERROR AL GENERAR CLIP");
      }

      const data = await response.json();
      setClipUrl(data.clip_url);
      
    } catch (error) {
      console.error("ERROR AL GENERAR CLIP", error);
    }
  };

  const handleCambioGenero = (event: React.ChangeEvent<HTMLSelectElement>) => {
    setGenero(event.target.value);
  };

  const handleCambioInstrumento = (
    event: React.ChangeEvent<HTMLSelectElement>
  ) => {
    setInstrumento(event.target.value);
  };
  //
  return (
    <div className="App">
      <h1 className="header">Dalia </h1>
      <div className="Select-gender">
        <label> Género: </label>
        <select id="genero" value={genero ?? ''} onChange={handleCambioGenero}>
          <option value="rock"> Rock </option>
          <option value="pop"> Pop </option>pero
          <option value="electrónica">Electrónica</option>
          <option value="clásica">Música Clásica</option>
        </select>
        <label> Instrumento: </label>
        <select
          id="instrumento"
          value={instrumento ?? '' }
          onChange={handleCambioInstrumento}
        >

          <option value="guitarra">Guitarra</option>
          <option value="piano">Piano</option>
          <option value="batería">Batería</option>
          <option value="violín">Violín</option>
          <option value="contrabajo">Contrabajo</option>
          <option value="flauta">Flauta</option>
        </select>
      </div>
      <button onClick={handleGenerarClip}> Generar audio</button>
      {clipUrl && (
        <div>
          <h2>Tu clip:</h2>
          <audio controls>
            <source src={clipUrl} type="audio/mpeg" />
            Tu navegador no soporta el elemento de audio.
          </audio>
        </div>
      )}
    </div>
  );
}

export default CreadorClip;
