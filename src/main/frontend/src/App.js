import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';
import Hello from './views/hello';

function App() {
  return (
    <Router>
      <Routes>
        <Route path="/hello" element={<Hello />}></Route>
      </Routes>
    </Router>
  );
}

export default App;
