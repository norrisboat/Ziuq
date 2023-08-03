//
//  SessionViewModel.swift
//  iosApp
//
//  Created by Norris Aboagye on 03/08/2023.
//  Copyright © 2023 orgName. All rights reserved.
//

import Foundation
import shared

class SessionViewModel: ObservableObject {
    static let shared = SessionViewModel()

    var settingsRepository = SettingsRepositoryImpl()
    
    @Published var isLoggedIn: Bool = false
    @Published var showHome: Bool = false
    
    init() {
        isUserLoggedIn()
    }
    
    func isUserLoggedIn() {
        isLoggedIn = settingsRepository.isLoggedIn()
    }
    
    func showHome(_ show: Bool) {
        showHome = show
    }
    
}
