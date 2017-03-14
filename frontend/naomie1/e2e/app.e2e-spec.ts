import { Naomie1Page } from './app.po';

describe('naomie1 App', () => {
  let page: Naomie1Page;

  beforeEach(() => {
    page = new Naomie1Page();
  });

  it('should display message saying app works', () => {
    page.navigateTo();
    expect(page.getParagraphText()).toEqual('app works!');
  });
});
