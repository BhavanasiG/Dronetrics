export function getFavicon() {
  return `
    <svg width="64" height="64" viewBox="0 0 64 64" fill="none" xmlns="http://www.w3.org/2000/svg">
      <defs>
        <linearGradient id="droneGradient" x1="0%" y1="0%" x2="100%" y2="100%">
          <stop offset="0%" stop-color="#3b82f6" />
          <stop offset="100%" stop-color="#1d4ed8" />
        </linearGradient>
        <linearGradient id="chartGradient" x1="0%" y1="0%" x2="100%" y2="100%">
          <stop offset="0%" stop-color="#10b981" />
          <stop offset="100%" stop-color="#059669" />
        </linearGradient>
      </defs>
      <circle cx="32" cy="32" r="30" fill="url(#droneGradient)" opacity="0.1" />
      <rect x="26" y="28" width="12" height="8" rx="2" fill="url(#droneGradient)" />
      <line x1="26" y1="32" x2="14" y2="20" stroke="url(#droneGradient)" stroke-width="2" stroke-linecap="round" />
      <line x1="38" y1="32" x2="50" y2="20" stroke="url(#droneGradient)" stroke-width="2" stroke-linecap="round" />
      <line x1="26" y1="32" x2="14" y2="44" stroke="url(#droneGradient)" stroke-width="2" stroke-linecap="round" />
      <line x1="38" y1="32" x2="50" y2="44" stroke="url(#droneGradient)" stroke-width="2" stroke-linecap="round" />
      <circle cx="14" cy="20" r="4" fill="url(#droneGradient)" />
      <circle cx="50" cy="20" r="4" fill="url(#droneGradient)" />
      <circle cx="14" cy="44" r="4" fill="url(#droneGradient)" />
      <circle cx="50" cy="44" r="4" fill="url(#droneGradient)" />
      <ellipse cx="14" cy="20" rx="6" ry="2" fill="url(#droneGradient)" opacity="0.4" />
      <ellipse cx="50" cy="20" rx="6" ry="2" fill="url(#droneGradient)" opacity="0.4" />
      <ellipse cx="14" cy="44" rx="6" ry="2" fill="url(#droneGradient)" opacity="0.4" />
      <ellipse cx="50" cy="44" rx="6" ry="2" fill="url(#droneGradient)" opacity="0.4" />
      <rect x="28" y="38" width="2" height="8" rx="1" fill="url(#chartGradient)" />
      <rect x="31" y="36" width="2" height="10" rx="1" fill="url(#chartGradient)" />
      <rect x="34" y="34" width="2" height="12" rx="1" fill="url(#chartGradient)" />
      <circle cx="29" cy="31" r="1" fill="#10b981" />
      <circle cx="32" cy="31" r="1" fill="#10b981" />
      <circle cx="35" cy="31" r="1" fill="#10b981" />
    </svg>
  `.trim();
}


/**
 * Injects the Dronetrics logo as the browser favicon.
 * The SVG was created on Figma
 */
export function setDynamicFavicon() {
  const encodedSvg = globalThis.btoa(getFavicon());
  const faviconUrl = `data:image/svg+xml;base64,${encodedSvg}`;

  let link = document.querySelector("link[rel~='icon']");
  if (!link) {
    link = document.createElement('link');
    link.rel = 'icon';
    document.getElementsByTagName('head')[0].appendChild(link);
  }
  link.href = faviconUrl;
}