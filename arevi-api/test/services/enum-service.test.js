const assert = require('assert');
const app = require('../../src/app');

describe('\'enum-service\' service', () => {
  it('registered the service', () => {
    const service = app.service('enum-service');

    assert.ok(service, 'Registered the service');
  });
});
