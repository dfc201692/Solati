import React from 'react';
import { BrowserRouter as Router, Route, Switch } from 'react-router-dom';
import App from './App';
import NotFound from './NotFound'; // Página de 404, si la necesitas

const Routes = () => {
  return (
    <Router>
      <Switch>
        <Route exact path="/" component={App} />
        {/* Agrega más rutas según sea necesario */}
        <Route component={NotFound} />
      </Switch>
    </Router>
  );
};

export default Routes;