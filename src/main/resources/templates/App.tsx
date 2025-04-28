import React, { useState, useRef, useEffect } from 'react';

function App() {
  const [username, setUsername] = useState('');
  const [password, setPassword] = useState('');
  const [captchaText, setCaptchaText] = useState('');
  const [userCaptcha, setUserCaptcha] = useState('');
  const [loginStatus, setLoginStatus] = useState<null | 'success' | 'error'>(null);
  const captchaRef = useRef<HTMLCanvasElement>(null);

  // Generate random captcha text
  const generateCaptcha = () => {
    const chars = '0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz';
    let result = '';
    for (let i = 0; i < 6; i++) {
      result += chars.charAt(Math.floor(Math.random() * chars.length));
    }
    setCaptchaText(result);
    return result;
  };

  // Draw captcha on canvas
  const drawCaptcha = () => {
    const canvas = captchaRef.current;
    if (!canvas) return;
    
    const ctx = canvas.getContext('2d');
    if (!ctx) return;
    
    ctx.clearRect(0, 0, canvas.width, canvas.height);
    const text = generateCaptcha();
    
    // Add background
    ctx.fillStyle = '#f8f9fa';
    ctx.fillRect(0, 0, canvas.width, canvas.height);
    
    // Draw the text with varying styles
    for (let i = 0; i < text.length; i++) {
      ctx.save();
      ctx.translate(15 + i * 18, 25);
      ctx.rotate((Math.random() - 0.5) * 0.4);
      
      const fontSize = window.innerWidth < 640 ? 16 : 20;
      ctx.font = `${fontSize + Math.floor(Math.random() * 6)}px Arial`;
      ctx.fillStyle = `rgb(
        ${Math.floor(Math.random() * 100)},
        ${Math.floor(Math.random() * 100)},
        ${Math.floor(Math.random() * 100)}
      )`;
      
      ctx.fillText(text[i], 0, 0);
      ctx.restore();
    }
    
    // Add noise dots
    for (let i = 0; i < 100; i++) {
      ctx.fillStyle = `rgba(
        ${Math.floor(Math.random() * 255)},
        ${Math.floor(Math.random() * 255)},
        ${Math.floor(Math.random() * 255)},
        ${Math.random() * 0.5}
      )`;
      ctx.fillRect(
        Math.random() * canvas.width,
        Math.random() * canvas.height,
        2,
        2
      );
    }
    
    // Add lines
    for (let i = 0; i < 5; i++) {
      ctx.strokeStyle = `rgba(
        ${Math.floor(Math.random() * 255)},
        ${Math.floor(Math.random() * 255)},
        ${Math.floor(Math.random() * 255)},
        ${Math.random() * 0.5}
      )`;
      ctx.beginPath();
      ctx.moveTo(Math.random() * canvas.width, Math.random() * canvas.height);
      ctx.lineTo(Math.random() * canvas.width, Math.random() * canvas.height);
      ctx.stroke();
    }
  };

  // Refresh captcha handler
  const refreshCaptcha = () => {
    drawCaptcha();
    setUserCaptcha('');
  };

  // Initialize captcha on component mount
  useEffect(() => {
    drawCaptcha();
    window.addEventListener('resize', drawCaptcha);
    return () => window.removeEventListener('resize', drawCaptcha);
  }, []);

  const handleSubmit = (e: React.FormEvent) => {
    e.preventDefault();
    setLoginStatus(null);
    
    if (!username || !password || !userCaptcha) {
      alert('Please fill in all fields');
      return;
    }
    
    // Check credentials and captcha (case insensitive)
    if (username === 'admin' && password === 'admin123' && userCaptcha.toLowerCase() === captchaText.toLowerCase()) {
      setLoginStatus('success');
    } else {
      setLoginStatus('error');
      refreshCaptcha();
    }
  };

  return (
    <div className="min-h-screen bg-gradient-to-br from-blue-50 to-white flex flex-col lg:flex-row">
      {/* Left Side - Logo Section - Hidden on small screens */}
      <div
        className="hidden lg:flex lg:w-1/2 items-center justify-center bg-white p-4 lg:p-12"
        style={{
          backgroundImage: "url('bg.jpg')",
          backgroundSize: "cover",
          backgroundPosition: "center",
        }}
      >
        <div className="max-w-md w-full">
          <div className="flex items-center justify-center mb-8 lg:mb-20">
            <img
              src="child.png"
              alt="Logo"
              className="w-32 h-32 lg:w-60 lg:h-60 object-contain"
            />
          </div>
          <h1 className="text-2xl lg:text-3xl xl:text-4xl font-bold text-center text-gray-900">
            Meghalaya State Commission for Protection of Child Rights
          </h1>
        </div>
      </div>

      {/* Right Side - Login Form */}
      <div className="w-full lg:w-1/2 flex items-center justify-center p-4 sm:p-8 min-h-screen">
        <div className="w-full max-w-md">
          <div className="bg-white rounded-xl sm:rounded-2xl shadow-md sm:shadow-xl p-4 sm:p-6 md:p-8">
            {/* Mobile Logo - Only shown on small screens */}
            <div className="flex flex-col items-center mb-6 lg:hidden">
              <img src="/child.png" alt="Child Logo" className="w-16 h-16" />
            </div>

            <h2 className="text-xl sm:text-2xl font-bold text-center text-gray-900 mb-4 sm:mb-6">
              Login
            </h2>
            
            <form onSubmit={handleSubmit} className="space-y-4 sm:space-y-6">
              {/* Username Field */}
              <div>
                <label htmlFor="username" className="block text-sm sm:text-base font-medium text-gray-700 mb-1 sm:mb-2">
                  Username
                </label>
                <input
                  type="text"
                  id="username"
                  value={username}
                  onChange={(e) => setUsername(e.target.value)}
                  className="w-full text-sm sm:text-base px-3 py-2 sm:px-4 sm:py-2 border border-gray-300 rounded-lg focus:ring-2 focus:ring-blue-500 focus:border-blue-500"
                  placeholder="Enter username"
                />
              </div>

              {/* Password Field */}
              <div>
                <label htmlFor="password" className="block text-sm sm:text-base font-medium text-gray-700 mb-1 sm:mb-2">
                  Password
                </label>
                <input
                  type="password"
                  id="password"
                  value={password}
                  onChange={(e) => setPassword(e.target.value)}
                  className="w-full text-sm sm:text-base px-3 py-2 sm:px-4 sm:py-2 border border-gray-300 rounded-lg focus:ring-2 focus:ring-blue-500 focus:border-blue-500"
                  placeholder="Enter your password"
                />
              </div>

              {/* Captcha Section */}
              <div>
                <label htmlFor="captcha" className="block text-sm sm:text-base font-medium text-gray-700 mb-1 sm:mb-2">
                  Captcha Verification
                </label>
                <div className="flex flex-col sm:flex-row items-start sm:items-center gap-2 sm:gap-3">
                  <div className="relative">
                    <canvas
                      ref={captchaRef}
                      width={window.innerWidth < 640 ? 120 : 150}
                      height={window.innerWidth < 640 ? 40 : 50}
                      className="border border-gray-300 rounded cursor-pointer"
                      onClick={refreshCaptcha}
                      title="Click to refresh captcha"
                    />
                    <button
                      type="button"
                      onClick={refreshCaptcha}
                      className="absolute -right-2 -top-2 p-1 bg-gray-200 rounded-full hover:bg-gray-300 transition-colors"
                      title="Refresh captcha"
                    >
                      <svg
                        xmlns="http://www.w3.org/2000/svg"
                        className="h-3 w-3 sm:h-4 sm:w-4 text-gray-600"
                        fill="none"
                        viewBox="0 0 24 24"
                        stroke="currentColor"
                      >
                        <path
                          strokeLinecap="round"
                          strokeLinejoin="round"
                          strokeWidth={2}
                          d="M4 4v5h.582m15.356 2A8.001 8.001 0 004.582 9m0 0H9m11 11v-5h-.581m0 0a8.003 8.003 0 01-15.357-2m15.357 2H15"
                        />
                      </svg>
                    </button>
                  </div>
                  <input
                    type="text"
                    id="captcha"
                    value={userCaptcha}
                    onChange={(e) => setUserCaptcha(e.target.value)}
                    className="flex-1 text-sm sm:text-base px-3 py-2 sm:px-4 sm:py-2 border border-gray-300 rounded-lg focus:ring-2 focus:ring-blue-500 focus:border-blue-500"
                    placeholder="Enter captcha"
                    autoComplete="off"
                  />
                </div>
                <p className="mt-1 text-xs text-gray-500">
                  Can't read? Click on the captcha or refresh button to get a new one
                </p>
              </div>

              {/* Login Status Message */}
              {loginStatus === 'success' && (
                <div className="p-3 text-base bg-green-100 text-green-700 rounded-lg text-center">
                  Login successful! Welcome {username}.
                </div>
              )}
              {loginStatus === 'error' && (
                <div className="p-3 text-base bg-red-100 text-red-700 rounded-lg text-center">
                  {username !== 'admin' 
                    ? 'Invalid username' 
                    : password !== 'admin123' 
                    ? 'Invalid password' 
                    : 'Invalid captcha'}
                </div>
              )}

              {/* Submit Button */}
              <button
                type="submit"
                className="w-full text-sm sm:text-base bg-blue-600 text-white py-2 px-4 rounded-lg hover:bg-blue-700 transition-colors duration-200"
              >
                Login
              </button>
            </form>
          </div>
        </div>
      </div>
    </div>
  );
}

export default App;