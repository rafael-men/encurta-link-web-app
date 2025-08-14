import React, { useMemo, useState } from 'react';
import { generateShortUrl } from '../services/shortenerService';
import ShortUrlsTable from '../components/PreviousTable';


const HomeEncurtador = () => {
const [longUrl, setLongUrl] = useState('');
  const [shortUrl, setShortUrl] = useState('');
  const [error, setError] = useState('');
  const [loading, setLoading] = useState(false);
  const [copied, setCopied] = useState(false);


  const [reloadTable, setReloadTable] = useState(0);

  const user = useMemo(() => {
    try {
      const u = JSON.parse(localStorage.getItem('user'));
      return u || {};
    } catch {
      return {};
    }
  }, []);

  const username =
    user?.username ||
    user?.name ||
    user?.sub ||
    'usuário';

  const isValidUrl = (value) => {
    try {
      const u = new URL(value);
      return !!u.protocol && !!u.host;
    } catch {
      return false;
    }
  };

  const handleShorten = async (e) => {
    e.preventDefault();
    setError('');
    setShortUrl('');
    setCopied(false);

    if (!longUrl.trim()) {
      setError('Cole uma URL para encurtar.');
      return;
    }
    if (!isValidUrl(longUrl.trim())) {
      setError('Informe uma URL válida (ex: https://exemplo.com/minha-pagina).');
      return;
    }

    setLoading(true);
    const result = await generateShortUrl({ originalUrl: longUrl.trim() });
    setLoading(false);

    if (result.success) {
      const data = result.data || {};
      if (!data.shortUrl) {
        setError('O servidor não retornou a URL encurtada.');
        return;
      }
      setShortUrl(data.shortUrl);
      setLongUrl('');
      setReloadTable((v) => v + 1);
    } else {
      setError(result.message || 'Erro ao gerar link curto.');
    }
  };

  const handleCopy = async () => {
    if (!shortUrl) return;
    try {
      await navigator.clipboard.writeText(shortUrl);
      setCopied(true);
      setTimeout(() => setCopied(false), 1500);
    } catch {
      const temp = document.createElement('input');
      temp.value = shortUrl;
      document.body.appendChild(temp);
      temp.select();
      document.execCommand('copy');
      document.body.removeChild(temp);
      setCopied(true);
      setTimeout(() => setCopied(false), 1500);
    }
  };

  return (
    <div className="min-h-screen bg-blue-900 flex items-center justify-center p-6">
      <div className="bg-white w-full max-w-xl rounded-2xl shadow-lg p-6">
        <div className="mb-6">
          <h1 className="text-2xl sm:text-3xl font-bold text-blue-700">
            Olá, {username}! 
          </h1>
          <p className="text-gray-600 mt-1">
            Cole abaixo o seu link longo para gerar um link curto.
          </p>
        </div>

        <form onSubmit={handleShorten} className="space-y-4">
          <div>
            <label className="block text-sm font-medium text-gray-700 mb-1">
              URL longa
            </label>
            <input
              type="url"
              placeholder="https://exemplo.com/pagina/muito/longa?param=valor"
              value={longUrl}
              onChange={(e) => setLongUrl(e.target.value)}
              className="w-full p-3 border border-gray-300 rounded-lg focus:outline-none focus:ring-2 focus:ring-blue-500"
            />
          </div>

          {error && (
            <div className="text-red-600 text-sm">{error}</div>
          )}

          <button
            type="submit"
            disabled={loading}
            className="w-full py-3 bg-blue-600 text-white rounded-lg hover:bg-blue-700 transition disabled:opacity-60 disabled:cursor-not-allowed"
          >
            {loading ? 'Gerando...' : 'Gerar link curto'}
          </button>
        </form>

        {!!shortUrl && (
          <div className="mt-6 border-t pt-6">
            <p className="text-sm text-gray-600 mb-2">Seu link curto:</p>

            <div className="flex flex-col sm:flex-row gap-3 items-stretch sm:items-center">
              <a
                href={shortUrl}
                target="_blank"
                rel="noopener noreferrer"
                className="flex-1 px-4 py-3 border rounded-lg text-blue-700 break-all hover:bg-blue-50 transition"
                title="Abrir link curto em nova aba"
              >
                {shortUrl}
              </a>

              <button
                onClick={handleCopy}
                className="px-4 py-3 bg-gray-900 text-white rounded-lg hover:bg-gray-800 transition"
              >
                {copied ? 'Copiado!' : 'Copiar'}
              </button>
            </div>

            <p className="text-xs text-gray-500 mt-2">
              Clique no link para abrir em nova aba ou use o botão para copiar.
            </p>
          </div>
        )}
         <div className="mt-10">
          <ShortUrlsTable reload={reloadTable} />
        </div>
      </div>
    </div>
  );
};

export default HomeEncurtador;
