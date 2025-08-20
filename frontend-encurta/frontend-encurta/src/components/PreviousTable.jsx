import React, { useEffect, useState } from "react"; 
import { getAllShortUrls } from "../services/shortenerService"; 
 
export default function ShortUrlsTable() { 
  const [urls, setUrls] = useState([]); 
  const [loading, setLoading] = useState(true); 
  const [error, setError] = useState(null); 
 
  useEffect(() => { 
    console.log("Iniciando busca de URLs..."); // Debug
    
    getAllShortUrls() 
      .then((res) => { 
        console.log("Resposta recebida:", res); // Debug
        
        if (res.success) { 
          console.log("URLs encontradas:", res.data); // Debug
          setUrls(res.data); 
        } else { 
          console.error("Erro na resposta:", res.message); // Debug
          setError(res.message || "Não foi possível carregar as URLs."); 
        } 
        setLoading(false); 
      }) 
      .catch((err) => { 
        console.error("Erro na requisição:", err); // Debug
        setError("Erro inesperado ao carregar as URLs."); 
        setLoading(false); 
      }); 
  }, []); 
 
  if (loading) { 
    return <p className="text-center text-blue-600">Carregando...</p>; 
  } 
 
  if (error) { 
    return ( 
      <div className="max-w-3xl mx-auto p-6 bg-white shadow-lg rounded-lg"> 
        <h2 className="text-xl font-bold text-blue-700 mb-2">Erro</h2> 
        <p className="text-red-600">{error}</p>
        <p className="text-sm text-gray-600 mt-2">Verifique o console para mais detalhes</p>
      </div> 
    ); 
  } 
 
  return ( 
    <div className="max-w-5xl mx-auto p-6 bg-white shadow-lg rounded-lg"> 
      <h2 className="text-2xl font-bold text-blue-700 mb-4 text-center"> 
        URLs Encurtadas ({urls.length} encontradas)
      </h2> 
 
      <div className="overflow-x-auto"> 
        <table className="min-w-full border border-gray-200 rounded-lg overflow-hidden"> 
          <thead className="bg-blue-100 text-blue-800"> 
            <tr> 
              <th className="py-3 px-4 text-left text-sm font-semibold"> 
                URL Original 
              </th> 
              <th className="py-3 px-4 text-left text-sm font-semibold"> 
                Código Curto 
              </th> 
            </tr> 
          </thead> 
          <tbody> 
            {urls.map((url, index) => ( 
              <tr 
                key={url.shortCode || index} 
                className="hover:bg-gray-50 border-t border-gray-200" 
              > 
                <td className="py-2 px-4 text-gray-700 break-all"> 
                  <a 
                    href={url.originalUrl} 
                    target="_blank" 
                    rel="noopener noreferrer" 
                    className="text-blue-600 hover:underline" 
                  > 
                    {url.originalUrl} 
                  </a> 
                </td> 
                <td className="py-2 px-4 font-mono text-gray-800"> 
                  {url.shortCode} 
                </td> 
              </tr> 
            ))} 
          </tbody> 
        </table> 
 
        {urls.length === 0 && ( 
          <p className="text-center text-gray-500 mt-4"> 
            Nenhuma URL encontrada. 
          </p> 
        )} 
      </div> 
    </div> 
  ); 
}