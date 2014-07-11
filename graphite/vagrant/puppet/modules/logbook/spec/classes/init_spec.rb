require 'spec_helper'
describe 'logbook' do

  context 'with defaults for all parameters' do
    it { should contain_class('logbook') }
  end
end
