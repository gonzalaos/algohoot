echo "window._env_ = { baseApiUrl: \"${VITE_API_URL}\", }" > "/usr/share/nginx/html/env-config.js"
exec nginx -g 'daemon off;'