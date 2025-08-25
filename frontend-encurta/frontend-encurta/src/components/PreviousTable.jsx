import React, { useEffect, useState } from "react";
import { getAllShortUrls } from "../services/shortenerService";

export default function ShortUrlsTable() {
  const [urls, setUrls] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);

  useEffect(() => {
    (async () => {
      try {
        const res = await getAllShortUrls();
        if (!res?.success) {
          throw new Error(res?.message || "Não foi possível carregar as URLs.");
        }
        setUrls(Array.isArray(res.data) ? res.data : []);
      } catch (err) {
        console.error("Erro ao carregar URLs:", err);
        setError(err.message || "Erro inesperado ao carregar as URLs.");
      } finally {
        setLoading(false);
      }
    })();
  }, []);

  return (
    <div className="max-w-5xl mx-auto p-6 bg-white shadow-lg rounded-lg">
      <h2 className="text-2xl font-bold text-blue-700 mb-4 text-center">
        URLs Encurtadas ({loading ? "..." : urls.length})
      </h2>

      {loading && (
        <div className="flex items-center justify-center p-8">
          <div className="animate-spin rounded-full h-8 w-8 border-b-2 border-blue-600"></div>
          <p className="ml-3 text-blue-600">Carregando URLs...</p>
        </div>
      )}

      {!loading && error && (
        <div className="max-w-3xl mx-auto p-6 bg-white shadow-lg rounded-lg">
          <h3 className="text-xl font-bold text-blue-700 mb-2">Erro</h3>
          <p className="text-red-600">{error}</p>
          <p className="text-sm text-gray-600 mt-2">Verifique o console para mais detalhes.</p>
        </div>
      )}

      {!loading && !error && (
        <div className="overflow-x-auto">
          <table className="min-w-full border border-gray-200 rounded-lg overflow-hidden">
            <thead className="bg-blue-100 text-blue-800">
              <tr>
                <th className="py-3 px-4 text-left text-sm font-semibold">ID</th>
                <th className="py-3 px-4 text-left text-sm font-semibold">URL Original</th>
              </tr>
            </thead>
            <tbody>
              {urls.map((url, index) => (
                <tr key={url.id || index} className="hover:bg-gray-50 border-t border-gray-200">
                  <td className="py-2 px-4 font-mono text-gray-800">{url.id}</td>
                  <td className="py-2 px-4 text-gray-700 break-all max-w-lg">
                    <a
                      href={url.originalUrl}
                      target="_blank"
                      rel="noopener noreferrer"
                      className="text-blue-600 hover:underline"
                      title={url.originalUrl}
                    >
                      {url.originalUrl?.length > 80
                        ? url.originalUrl.substring(0, 80) + "…"
                        : url.originalUrl}
                    </a>
                  </td>
                </tr>
              ))}
            </tbody>
          </table>

          {urls.length === 0 && (
            <p className="text-center text-gray-500 mt-4">Nenhuma URL encontrada.</p>
          )}
        </div>
      )}
    </div>
  );
}
