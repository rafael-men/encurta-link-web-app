import { BrowserRouter as Router, Route, Routes, Navigate } from 'react-router-dom';
import Login from './pages/Login';
import './index.css';
import Register from './pages/Register';
import HomeEncurtador from './pages/Home';

function App() {

  return (
    <Router>
      <Routes>
        <Route path="/" element={<Navigate to="/login" />} />
        <Route path="/login" element={<Login />} />
        <Route path="/register" element={<Register/>} />
        <Route path="/home-encurtador" element={<HomeEncurtador/>}/>
      </Routes>
    </Router>
  )
}

export default App
